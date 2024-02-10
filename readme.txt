Link para a planilha: https://docs.google.com/spreadsheets/d/1iDbTCb7om3ZQpHSKYXHN2sADc5mOTH2dqhz0nnclJnY/edit?pli=1#gid=0

É possível visualizar/utilizar esse projeto de 2 maneiras:

A - Através de um EC2 na AWS (Nuvem).
B - Clonando o projeto em sua maquina local.


A - Utilizando o EC2:

1- Abra o terminal do Linux, ou utilize o WSL no Windows.

2- Copie e cole o seguinte comando:

ssh -i "aws-oregon.pem" ubuntu@ec2-34-220-175-248.us-west-2.compute.amazonaws.com

Quando for solicitado a senha digite : tuntsrocks

3 - Após entrar no EC2, copie e cole o seguinte comando:

cd ~/project/TuntsRocks2024 && gradle run




B - Usando o projeto em sua maquina:

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

3- Instale o JDK 17 (Caso tenha instalado o Java 17, ou superior instalado, pule para a Etapa 4)

choco install openjdk17

Siga as instruções na tela e, quando solicitado, digite "A" e pressione Enter para confirmar a instalação.
Aguarde até que o processo de instalação seja concluído.
Feche o Command Prompt e abra novamente como Administrador.


4 - Navegue até a pasta principal do projeto:

No Command Prompt, digite o comando cd seguido do caminho para a pasta onde está o seu projeto.
Por exemplo:
cd C:\Users\SeuUsuario\Desktop\TuntsRocks2024

5 - Após navegar até a pasta correta, digite o seguinte comando:

gradle run