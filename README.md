# SD API Serviço Nomes

## Descrição

O **SD API Serviço Nomes** é o servidor de descoberta de serviços (Service Discovery) da arquitetura de microsserviços do Sistema de Delivery de Comida.

O projeto utiliza o Netflix Eureka Server para permitir que os microsserviços registrem suas instâncias e descubram outros serviços dinamicamente, eliminando a necessidade de configurar endereços IP e portas manualmente.

---

# Objetivo

Centralizar o registro e descoberta de serviços da arquitetura.

Com o Eureka Server, os microsserviços podem:

* Registrar suas instâncias automaticamente.
* Descobrir outros microsserviços.
* Trabalhar com escalabilidade horizontal.
* Realizar balanceamento de carga.
* Reduzir acoplamento entre serviços.

---

# Tecnologias Utilizadas

* Java 21
* Spring Boot 4.0.6
* Spring Cloud Netflix Eureka Server
* Maven

---

# Arquitetura

```text
                     +----------------------+
                     |  Eureka Server       |
                     | SD API Serviço Nomes |
                     +----------+-----------+
                                |
        -------------------------------------------------
        |                |               |              |
        ▼                ▼               ▼              ▼
 SD-API-PEDIDO   MICROSSERVICO-    SD-API-ENTREGA  SD-NOTIFICACAO
                  USUARIOS
```

Todos os microsserviços registram-se no Eureka durante a inicialização.

---

# Configuração

## Nome da Aplicação

```properties
spring.application.name=sd-api-servico-nomes
```

## Porta do Servidor

```properties
server.port=8761
```

A aplicação fica disponível em:

```text
http://localhost:8761
```

---

# Configurações do Eureka

```properties
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

## register-with-eureka=false

Impede que o próprio servidor Eureka tente registrar-se nele mesmo.

## fetch-registry=false

Impede que o servidor Eureka tente buscar registros de outros servidores Eureka.

Essas configurações são recomendadas quando existe apenas uma instância do servidor Eureka.

---

# Painel Administrativo

Após iniciar a aplicação, o painel pode ser acessado em:

```text
http://localhost:8761
```

O painel permite visualizar:

* Serviços registrados
* Instâncias disponíveis
* Status dos microsserviços
* Informações de disponibilidade

---

# Comunicação entre Microsserviços

O Eureka não participa diretamente da comunicação.

Seu papel é fornecer informações sobre localização dos serviços.

Exemplo:

```text
SD-API-PEDIDO
       │
       ▼
Consulta Eureka
       │
       ▼
MICROSSERVICO-USUARIOS
192.168.1.10:8081
```

Após descobrir o endereço, o microsserviço realiza a comunicação diretamente.

---

# Invocação Remota (RPC)

O Eureka não realiza RPC.

Ele fornece apenas o mecanismo de descoberta de serviços.

A comunicação RPC é executada pelos microsserviços utilizando:

* REST
* gRPC

---

# Comunicação gRPC

O servidor Eureka não participa da comunicação gRPC.

Entretanto, ele pode ser utilizado para localizar serviços gRPC através do registro de metadados contendo a porta gRPC.

Exemplo:

```properties
eureka.instance.metadata-map.grpc.port=8083
```

---

# Mensageria e Eventos

O Eureka não produz nem consome eventos.

A comunicação assíncrona utilizando RabbitMQ ocorre diretamente entre os microsserviços.

Exemplos:

* Eventos de pagamento
* Eventos de entrega
* Notificações
* Atualizações de status

---

# Estrutura do Projeto

```text
src
└── main
    ├── java
    │   └── br.ifg.urutai.sdapiserviconomes
    │       └── SdApiServicoNomesApplication.java
    │
    └── resources
        └── application.properties
```

---

# Classe Principal

## SdApiServicoNomesApplication

Classe responsável por iniciar o servidor Eureka.

Funções:

* Inicialização do Eureka Server.
* Registro de serviços.
* Gerenciamento das instâncias registradas.
* Disponibilização do painel administrativo.

---

# Dependências Principais

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

Responsável por fornecer toda a infraestrutura do servidor Eureka.

---

# Como Executar

## Pré-requisitos

* Java 21
* Maven

## Executando

```bash
mvn clean install
mvn spring-boot:run
```

ou

```bash
java -jar target/sd-api-servico-nomes-1.0.0.jar
```

---

# Benefícios do Eureka

* Descoberta automática de serviços.
* Redução de configurações manuais.
* Escalabilidade.
* Integração com Spring Cloud.
* Suporte a balanceamento de carga.
* Maior tolerância a falhas.

---

# Responsabilidade do Microsserviço

O SD API Serviço Nomes é responsável exclusivamente pelo gerenciamento e descoberta dos microsserviços da arquitetura, servindo como ponto central de registro para todos os serviços do Sistema de Delivery de Comida.
