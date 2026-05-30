##Sistema de Gerenciamento de Biblioteca

#API REST desenvolvida em Java 21 com Spring Boot 3.5 para o gerenciamento de acervo de livros, usuários e controle automatizado de empréstimos e devoluções.

#Diferenciais e Regras de Negócio Implementadas
O projeto integra validações e práticas de arquitetura de mercado:

#Controle Automático de Estoque: O sistema gerencia o campo quantidade da entidade Livro, decrementando no empréstimo (e barrando a operação se o estoque for 0) e incrementando na devolução automaticamente.

#Limite de Empréstimos por Usuário: Trava de segurança que impede que uma pessoa tenha mais de 3 livros emprestados simultaneamente.

#Integração com API Externa (ViaCEP): No cadastro de pessoas, o usuário informa apenas o CEP e o sistema preenche o endereço completo de forma automatizada.

#Segurança com Spring Security e BCrypt: Autenticação com persistência de dados e criptografia de senhas via hash BCrypt antes do armazenamento no banco de dados.

#Documentação Interativa (Swagger): Interface completa exposta publicamente para testes rápidos e dinâmicos dos endpoints.

#Containerização com Docker: Projeto pronto para produção, garantindo que a API execute em qualquer ambiente de forma isolada com um único comando.

#Decisão de Arquitetura: Banco de Dados Volátil e Database-as-Code
A modelagem do banco de dados deste projeto foi desenhada seguindo os princípios de uma Arquitetura Voltada para Microsserviços e Containers.

#Em vez de acoplar a aplicação a um SGBD tradicional e centralizado em disco (como o MySQL), a API utiliza o H2 Database configurado estritamente em memória RAM (jdbc:h2:mem:bibliotecadb), gerenciado em conjunto com o Flyway.

#Justificativa da Escolha
Independência de Estado e Efemeridade: Alinhado ao conceito de microsserviços modernos, o banco de dados nasce e morre junto com o ciclo de vida do contêiner. Isso garante um ambiente de execução isolado, sem o acúmulo de dados residuais de testes passados que poderiam comprometer as regras de validação do sistema.

#Database-as-Code e Portabilidade Total: Toda a estrutura relacional do ecossistema está versionada em código puro através de scripts de migração do Flyway. Isso elimina qualquer necessidade de instalação, configuração ou intervenção manual em ferramentas visuais externas (como o MySQL Workbench) por parte do avaliador.

#Escalabilidade e CI/CD: A arquitetura torna o serviço portátil, leve e ideal para esteiras de Integração Contínua (CI/CD), permitindo que múltiplas instâncias da API sejam escaladas horizontalmente em segundos, já que cada contêiner carrega seu próprio motor relacional autônomo na inicialização.