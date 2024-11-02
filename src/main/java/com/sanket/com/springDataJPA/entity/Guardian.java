package com.sanket.com.springDataJPA.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverrides({
        @AttributeOverride(name = "name",// my specified name
                column = @Column(name = "guardian_name")),// database table name

        @AttributeOverride(name = "email",
                column = @Column(name = "guardian_email")),

        @AttributeOverride(name = "mobile",
                column = @Column(name = "guardian_mobile"))
})
public class Guardian {
    private String name;  // my specified name
    private String email;
    private String mobile;
}


// @AttributeOverride format

//@AttributeOverrides({
//        @AttributeOverride(name = "",
//        column = @Column(name = "")),
//        ...................
//
//
//})
