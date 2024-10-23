package com.example.koifarm.repository;

import com.example.koifarm.entity.Feedback;
import com.example.koifarm.model.FeedBackResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    // Fetch feedback based on the user who is the customer
    @Query("SELECT new com.example.koifarm.model.FeedBackResponse(f.id, f.content, f.rating, a.email) " +
            "FROM Feedback f JOIN f.customer a WHERE a.id = :userId")
    List<FeedBackResponse> findFeedbackByUserId(@Param("userId") Long userId);
}
