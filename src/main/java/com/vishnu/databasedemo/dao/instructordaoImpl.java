package com.vishnu.databasedemo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vishnu.databasedemo.entity.InstructorDetail;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class instructordaoImpl implements instructordao {

    private EntityManager entityManager;

    
    @Autowired
    public instructordaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(InstructorDetail instructorDetail) {
        entityManager.persist(instructorDetail);
    }

    @Override
    public List<InstructorDetail> getInstructorDetailList(int key) {
        TypedQuery<InstructorDetail> instructorlistquery = entityManager.createQuery("FROM InstructorDetail where id < :idValue", InstructorDetail.class);
        instructorlistquery.setParameter("idValue",key);
        return instructorlistquery.getResultList();
    }

    @Override
    public InstructorDetail getInstructorDetailbyId(int key) {
        TypedQuery<InstructorDetail> instructorquey = entityManager.createQuery("from InstructorDetail where id = :idValue",InstructorDetail.class);
        instructorquey.setParameter("idValue",key);
        InstructorDetail instructorDetail = new InstructorDetail();
        try {
          instructorDetail =  instructorquey.getSingleResult(); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
            instructorDetail=null;
        }
        return instructorDetail;  
    }

    @Override
    @Transactional
    public void update(InstructorDetail instructorDetail) {
        entityManager.merge(instructorDetail);
    }

    @Override
    @Transactional
    public void deleteInstructor(int key) {
        InstructorDetail instructorDetail = entityManager.find(InstructorDetail.class, key);
        entityManager.remove(instructorDetail);
    }

    @Override
    @Transactional
    public int deletebyHobby(String hobby) {
        Query deleteInstructors = entityManager.createQuery("Delete from InstructorDetail where hobby like :hobbyString");
        deleteInstructors.setParameter("hobbyString","%"+hobby+"%");
        return deleteInstructors.executeUpdate();
    }

}
