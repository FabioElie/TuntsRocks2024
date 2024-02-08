import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class demonstrates the usage of Google Sheets API in Java.
 * It retrieves data from a Google Spreadsheet, processes it, and updates the spreadsheet with calculated results.
 *
 * @author Fabio Elie
 * @version 1.1
 */
public class SheetsQuickstart {

    /**
     * The name of the application.
     */
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

    /**
     * The JSON factory to use for creating JSON parsers and generators.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    /**
     * The path where the tokens will be stored.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * The scopes required by this application.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    /**
     * The path to the credentials file.
     */
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Retrieves the credentials for accessing Google Sheets API.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found or read.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Retrieves the Sheets service.
     *
     * @return The Sheets service.
     * @throws IOException              If an I/O error occurs.
     * @throws GeneralSecurityException If a security error occurs.
     */
    public static Sheets getSheetService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        // Build a new authorized API client service.
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


    /**
     * Calculates the result based on rounded absences percentage and rounded average.
     *
     * @param roundedAbsencesPercentage The rounded percentage of absences.
     * @param roundedAverage            The rounded average.
     * @return The result based on the given parameters.
     */
    public static String calculateResult(double roundedAbsencesPercentage, double roundedAverage) {

        String result;

        //Using absolute value to ensure that even negative values are handled correctly
        roundedAbsencesPercentage = Math.abs(roundedAbsencesPercentage);
        roundedAverage = Math.abs(roundedAverage);

        if (roundedAbsencesPercentage > 25) {
            result = "Reprovado por Falta";
        } else if (roundedAverage < 5) {
            result = "Reprovado por Nota";
        } else if (roundedAverage < 7) {
            result = "Exame Final";
        } else {
            result = "Aprovado";
        }

        return result;
    }

    /**
     * The main method that demonstrates the usage of Google Sheets API.
     *
     * @param args The command-line arguments.
     * @throws IOException              If an I/O error occurs.
     * @throws GeneralSecurityException If a security error occurs.
     */
    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Configure the system default encoding to avoid encoding errors
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        // Get the Google Sheets service
        Sheets sheetsService = getSheetService();

        // Spreadsheet ID
        final String spreadsheetId = "1iDbTCb7om3ZQpHSKYXHN2sADc5mOTH2dqhz0nnclJnY";

        //Range refers to all cells in sheet
        final String range = "engenharia_de_software";

        // Get values from the spreadsheet
        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();

        // Extract values from the response
        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            // Get the header row
            List<Object> headerRow = values.get(2);
            // Index of relevant columns
            int registrationIndex = headerRow.indexOf("Matricula");
            int studentIndex = headerRow.indexOf("Aluno");
            int absencesIndex = headerRow.indexOf("Faltas");
            int p1Index = headerRow.indexOf("P1");
            int p2Index = headerRow.indexOf("P2");
            int p3Index = headerRow.indexOf("P3");

            // Print the header
            System.out.printf("%n|%-10s|%-14s|%-8s|%-5s|%-5s|%-5s|%-5s|%-25s|%-25s|%n", headerRow.get(0), headerRow.get(1),
                    headerRow.get(2), headerRow.get(3), headerRow.get(4), headerRow.get(5), "Media", headerRow.get(6), headerRow.get(7));

            // Process the data
            for (int i = 3; i < values.size(); i++) {
                List<Object> row = values.get(i);
                String registration = row.get(registrationIndex).toString();
                String student = row.get(studentIndex).toString();
                int absences = Integer.parseInt((String) row.get(absencesIndex));
                int p1 = Integer.parseInt((String) row.get(p1Index));
                int p2 = Integer.parseInt((String) row.get(p2Index));
                int p3 = Integer.parseInt((String) row.get(p3Index));

                double average = (double) ((p1 + p2 + p3) / 3) / 10;
                double roundedAverage = Math.ceil(average);

                int totalNumberOfClasses = 60;
                double absencesPercentage = ((double) absences / totalNumberOfClasses) * 100;
                double roundedAbsencesPercentage = Math.ceil(absencesPercentage);

                double naf;

                // Calling the method and assigning results based on criteria
                String result = calculateResult(roundedAbsencesPercentage, roundedAverage);
                if (result.equals("Exame Final")) {
                    naf = (10 - roundedAverage);
                } else {
                    naf = 0;
                }


                // Print the results
                System.out.printf("|%-10s|%-14s|%-8s|%-5s|%-5s|%-5s|%-5s|%-25s|%-25s|%n", registration, student, absences, p1, p2, p3, roundedAverage, result, naf);


                //Create a list of the updated values to send back to the spreadsheet
                List<Object> updatedRowValues = Arrays.asList(registration, student, absences, p1, p2, p3, result, naf);

                //Create a ValueRange object with the updated values
                ValueRange body = new ValueRange().setValues(Collections.singletonList(updatedRowValues));

                // Execute the update
                sheetsService.spreadsheets().values()
                        .update(spreadsheetId, "engenharia_de_software!A" + (i + 1) + ":H" + (i + 1), body)
                        .setValueInputOption("RAW")
                        .execute();

            }
        }
    }
}

