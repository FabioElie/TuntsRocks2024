

Abra o Command Prompt (CMD) como administrador:


1- Instale o Chocolatey (Caso tenha instalado, pule para a Etapa 2) utilizando o comando abaixo:

@"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -ExecutionPolicy Bypass -Command "[System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))"

Aguarde até que o processo de instalação seja concluído.
Feche o Command Prompt e abra novamente como Administrador.



2-Instale o Gradle usando o Chocolatey (Caso tenha instalado, pule para a Etapa 3) utilizando o comando:

choco install gradle

Siga as instruções na tela e, quando solicitado, digite "A" e pressione Enter para confirmar a instalação.
Aguarde até que o processo de instalação seja concluído.
Feche o Command Prompt e abra novamente como Administrador.

3- Instale o JDK 17 (Caso tenha instalado o Java instalado, pule para a Etapa 4)

choco install openjdk17

Siga as instruções na tela e, quando solicitado, digite "A" e pressione Enter para confirmar a instalação.
Aguarde até que o processo de instalação seja concluído.
Feche o Command Prompt e abra novamente como Administrador.


4 - Navegue até a pasta principal do projeto:

No Command Prompt, digite o comando cd seguido do caminho para a pasta onde está o seu projeto.
Por exemplo:
cd C:\Users\SeuUsuario\Desktop\TuntsRocks2024

5 - Após navegar até a pasta correta, digite o seguinte comando:

gradlew run