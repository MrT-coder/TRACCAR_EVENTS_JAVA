# Usa la imagen oficial de RabbitMQ con gestión
FROM rabbitmq:3-management

# Habilita el plugin consistent hash exchange de forma offline
RUN rabbitmq-plugins enable --offline rabbitmq_consistent_hash_exchange
