package io.tasks.model;

import java.time.LocalDate;
import java.util.Set;

public class Task {

    private Long id;
    private String description;
    private LocalDate deadline;
    private LocalDate createdAt;
    private Category category;
    private Status status;
    private LocalDate canceledAt;

    public Task(Long id, String description, LocalDate deadline, LocalDate createdAt, Category category, Status status, LocalDate canceledAt) {
        this.id = id;
        this.description = description;
        this.deadline = deadline;
        this.createdAt = createdAt;
        this.category = category;
        this.status = status;
        this.canceledAt = canceledAt;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(LocalDate canceledAt) {
        this.canceledAt = canceledAt;
    }

    public void setId(long id) {
        this.id = id;
    }
}
