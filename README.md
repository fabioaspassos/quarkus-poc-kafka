# POC KAFKA - RATELIMIT - QUARKUS

Este é um projeto que implementa uma abordagem simples de ratelimit no consumo de topicos do kafka.

Estrutura de diretorio


/docker

contem um docker-compose com requisitos necessario para rodar um kafka server.


/producer

Aplicacao produtora de mensagens


/consumer

Aplicacao consumidora de mensagens e que impelentar uma abordagem de taxa de consumo.