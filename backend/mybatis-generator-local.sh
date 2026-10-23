./mvnw mybatis-generator:generate \
  -Djdbc.DriverClass=com.mysql.cj.jdbc.Driver \
  -Djdbc.jdbc.Url="jdbc:mysql://localhost:3306/jseter?useSSL=false&allowPublicKeyRetrieval=true" \
  -Djdbc.Username=jseter \
  -Djdbc.Password=jester
