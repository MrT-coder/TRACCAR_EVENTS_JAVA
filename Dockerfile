# Usa Debian 12.10 como imagen base
FROM debian:12.10

# Instala OpenJDK 17
RUN apt-get update && apt-get install -y openjdk-17-jdk && apt-get clean

# Crea un directorio de trabajo
WORKDIR /app

# Copia el JAR generado por Maven al contenedor
# Ajusta el nombre del JAR según lo que genere tu proyecto
COPY target/Events-0.0.1-SNAPSHOT.jar events.jar

# Expone el puerto en el que corre tu microservicio (definido en application.properties)
EXPOSE 8082

# Comando de arranque
ENTRYPOINT ["java", "-jar", "events.jar"]
