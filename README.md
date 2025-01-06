# FinancialWorksheet

## Descrição do Projeto

**FinancialWorksheet** é uma solução automática para capturar dados de transações financeiras realizadas no aplicativo do Nubank (como pagamentos e transferências) e enviá-los para um webhook configurado no **n8n**. O n8n processa esses dados, identificando o tipo de transação (entrada ou saída), e os insere automaticamente em uma planilha do **Google Sheets**.

O projeto utiliza:
- **Flutter** para criar o aplicativo móvel.
- **Kotlin** para implementar funcionalidades nativas que monitoram as notificações do aplicativo do Nubank em segundo plano.

## Funcionalidades

- **Monitoramento de Notificações**: O aplicativo detecta novas notificações do Nubank para capturar informações de transações.
- **Envio para Webhook**: Dados das transações (valor e fonte) são enviados automaticamente para o n8n.
- **Classificação Automática**: O n8n identifica se a transação é uma entrada ou saída.
- **Atualização em Planilha**: Dados são registrados em uma planilha do Google Sheets, separados por tipo de transação.
- **Execução em Segundo Plano**: O monitoramento funciona continuamente, mesmo com o aplicativo fechado.

## Tecnologias Utilizadas

### Aplicativo Móvel
- **Flutter**: Interface do aplicativo móvel.
- **Kotlin**: Integração nativa no Android para monitorar notificações em segundo plano.

### Backend e Integração
- **n8n**: Automatiza o processamento dos dados recebidos e organiza os registros na planilha.
- **Google Sheets API**: Para escrita e organização de dados na planilha.

## Fluxo de Funcionamento

1. **Captura de Notificações**:
   - O aplicativo detecta notificações financeiras do Nubank.
2. **Envio para Webhook**:
   - Os dados são enviados para um endpoint configurado no n8n.
3. **Processamento no n8n**:
   - O webhook recebe os dados e classifica a transação como entrada ou saída.
   - Os valores e fontes das transações são organizados e enviados para uma planilha do Google Sheets.

## Configuração do Projeto

### Requisitos
- **Flutter SDK** instalado.
- Ambiente de desenvolvimento Android configurado.
- Conta no **n8n** e **Google Sheets API** habilitada.

### Instalação

1. Clone o repositório:
   ```bash
   git clone https://github.com/seuusuario/FinancialWorksheet.git
   cd FinancialWorksheet
   ```

2. Configure o aplicativo:
   - No diretório `android`, configure as permissões no arquivo `AndroidManifest.xml` para monitorar notificações.

3. Configure o webhook no n8n:
   - Crie um fluxo no n8n que receba dados do webhook e atualize a planilha do Google Sheets.

4. Inicie o aplicativo:
   ```bash
   flutter run
   ```

## Uso

1. Instale e configure o aplicativo no seu dispositivo.
2. Conceda permissões de acesso às notificações do Nubank.
3. Configure o webhook no n8n com o link fornecido pelo aplicativo.
4. Monitore as atualizações na planilha do Google Sheets em tempo real.

