FROM openjdk:17
ADD target/productservice-0.0.1.jar ./productservice-0.0.1.jar
ENTRYPOINT [ "java", "-jar", "productservice-0.0.1.jar" ]