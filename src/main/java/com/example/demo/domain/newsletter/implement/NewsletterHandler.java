package com.example.demo.domain.newsletter.implement;

import com.example.demo.domain.newsletter.entity.NewsletterSubscription;
import com.example.demo.domain.newsletter.repository.NewsletterRepository;
import com.example.demo.global.base.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.demo.global.base.exception.ErrorCode.SUBSCRIBE_EMAIL_CONFLICT;

@Component
@RequiredArgsConstructor
public class NewsletterHandler {
    private final NewsletterRepository newsletterRepository;

    public void saveNewsletterSubscription(NewsletterSubscription newsletterSubscription) {
        newsletterRepository.saveNewsletterSubscription(newsletterSubscription);
    }

    public void updateNewsletterSubscription(NewsletterSubscription newsletterSubscription) {
        newsletterRepository.updateNewsletterSubscription(newsletterSubscription);
    }

    public void deleteNewsletterSubscription(String email) {
        newsletterRepository.deleteNewsletterSubscription(email);
    }

    public boolean existsNewsletterSubscription(String email) {
        return newsletterRepository.existsNewsletterSubscription(email);
    }
}
