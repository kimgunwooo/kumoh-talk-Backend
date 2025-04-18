package com.example.demo.infra.board.entity;

import com.example.demo.domain.notification.entity.vo.NotificationType;
import com.example.demo.infra.notification.repository.impl.NotificationRepositoryImpl;
import jakarta.persistence.PreRemove;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class LikeEntityListener {

    @Lazy
    @Autowired
    private NotificationRepositoryImpl notificationRepository;

    @PreRemove
    public void preRemove(Object comment) {
        if (comment instanceof Like like) {
            if (like.getDeletedAt() == null) {
                notificationRepository.deleteByInvokerIdAndInvokerType(like.getId(), NotificationType.BOARD_LIKE);
            }
        }
    }
}
