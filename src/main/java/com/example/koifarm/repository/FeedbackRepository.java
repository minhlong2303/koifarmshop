package com.example.koifarm.repository;

import com.example.koifarm.entity.Feedback;
import com.example.koifarm.model.FeedBackResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT new com.example.koifarm.model.FeedBackResponse(f.id, f.content,f.rating,a.email) " +
            "FROM Feedback  f join User a on f.shop.id = a.id where f.shop.id =:shopID")
    List<FeedBackResponse> findFeedbackByShopId(@Param("shopID") Long shopID);
}
