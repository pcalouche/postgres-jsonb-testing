# PostgreSQL JSONB Testing Options with H2 and Test Containers

## Testing JSONB Column Solutions

Each solution relies on the [Hibernate Types Library](https://github.com/vladmihalcea/hibernate-types) for `jsonb`
columns. This document is also
helpful: [How to map JSON objects using generic Hibernate Types](https://vladmihalcea.com/how-to-map-json-objects-using-generic-hibernate-types/)

```java
import com.pcalouche.testcontainers.dto.CustomerDetails;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class) // Works for both json and jsonb types
})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Type(type = "json")
    @Column(columnDefinition = "jsonb") // specify json or jsonb in the column definition
    private CustomerDetails customerDetails;
}
```

Liquibase is used to seed the data for the application and the tests. You can control what runs for the app and for test
by making use of the `spring.liquibase.contexts` property and applying that accordingly on change sets.

```xml 
<changeSet id="0" author="psc" context="test"/>
```

### H2 in Memory DB Solution

A special change set is needed to tell H2 to use a `json` column which is supports instead of a `jsonb` column which is
does NOT support.

```xml 
<changeSet id="0" author="psc" context="test" dbms="h2">
    <sql>
        CREATE TYPE IF NOT EXISTS "JSONB" as json;
    </sql>
</changeSet>
```

Pros:

- Fast

Cons:

- Not using an actual PostgresDB and alternating the schema some which may hide bugs during testing.

### PostgresSQL Test Containers Solution

Pros:

- Uses a real PostgreSQL database set to the version of your choosing like what you'd use in our real app.
- Can be used interchangeably with H2 for testing entities that do not contain `jsonb` columns if test speed is a
  concern.

Cons:

- Slower. Each test class that uses a PostgreSQL test container must wait for that to start up.
- Requires Docker to be present. Maybe not that big a deal these days.

### Reference Documentation

For further reference, please consider the following sections:

* [How to map JSON objects using generic Hibernate Types](https://vladmihalcea.com/how-to-map-json-objects-using-generic-hibernate-types/)
* [Testcontainers Postgres Module Reference Guide](https://www.testcontainers.org/modules/databases/postgres/)
* [Testcontainers](https://www.testcontainers.org/)
