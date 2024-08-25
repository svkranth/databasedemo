package com.vishnu.databasedemo.dao;

import java.util.List;

import com.vishnu.databasedemo.entity.InstructorDetail;

public interface instructordao {
    void save(InstructorDetail instructorDetail);
    List<InstructorDetail> getInstructorDetailList(int key);
    InstructorDetail getInstructorDetailbyId(int key);
    void update(InstructorDetail instructorDetail);
    void deleteInstructor(int key);
    int deletebyHobby(String hobby);
}
