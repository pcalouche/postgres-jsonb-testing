package com.pcalouche.testcontainers.entity;

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
        @TypeDef(name = "json", typeClass = JsonType.class)
})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private CustomerDetails customerDetails;
}
