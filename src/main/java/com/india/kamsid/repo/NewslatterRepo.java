package com.india.kamsid.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.india.kamsid.entities.NewsletterSubscription;

public interface NewslatterRepo extends JpaRepository<NewsletterSubscription, Long> {

	boolean existsByEmail(String email);
}
