package com.example.demo.infra.notification.entity;

import com.example.demo.global.base.domain.BaseEntity;
import com.example.demo.infra.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications_users")
@NoArgsConstructor
@Getter
public class NotificationUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Builder
    public NotificationUser(Notification notification, User user, boolean isRead) {
        this.notification = notification;
        this.user = user;
        this.isRead = isRead;
    }

    public NotificationUser(Notification notification, Long userId) {
        this.notification = notification;
        this.user = new User(userId);
        this.isRead = false;
    }
}
