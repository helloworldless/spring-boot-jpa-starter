package com.davidagood.springbootjpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "relation")
@NoArgsConstructor
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "parentId", nullable = false)
    private Integer parentId;

    @Column(name = "childId", nullable = false)
    private Integer childId;

    @Column(name = "createdAt", nullable = false, insertable = false)
    private OffsetDateTime createdAt;

    public Relation(int parentId, int childId) {
        this.parentId = parentId;
        this.childId = childId;
    }

}
