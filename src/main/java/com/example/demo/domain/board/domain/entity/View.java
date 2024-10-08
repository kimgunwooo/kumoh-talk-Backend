package com.example.demo.domain.board.domain.entity;


import com.example.demo.global.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "views")
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE views SET deleted_at = NOW() where id=?")
@SQLRestriction(value = "deleted_at is NULL")
public class View extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public View(Board board) {
        this.board = board;
        board.getViews().add(this);
    }
}
