package com.vishnu.databasedemo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.vishnu.databasedemo.dao.instructordao;
import com.vishnu.databasedemo.entity.InstructorDetail;

@SpringBootApplication
public class DatabasedemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabasedemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(instructordao instructordao){
		return runner -> {
			String path = System.getProperty("user.home") + "\\Desktop\\story";
			try{
				int numberofrecordsinserted = saveInstructor(path, instructordao);
				System.out.println("number of records inserted into table: "+numberofrecordsinserted);
				updateInstructor(25, instructordao);
				deleteInstructorbyId(32, instructordao);
				int numberofrecordsextracted = writeinstructors(path, instructordao, 100);
				System.out.println("number of records selected from table: "+ numberofrecordsextracted);
				InstructorDetail instructorDetail = instructordao.getInstructorDetailbyId(32);
				if(instructorDetail == null){
					System.out.println("Instructor is not found");
				}else{
					System.out.println("Youtube channel: "+ instructorDetail.getYouTubeChannel() + "hobby: " + instructorDetail.getHobby());
				}
				int deletedcount = deletespecifiedHobby("test", instructordao);
				System.out.println("number of rows deleted: "+ deletedcount);
			}catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		};
	}

	private int saveInstructor(String path, instructordao instructordao) throws Exception{
		int count=0;
		String line;
		BufferedReader reader = new BufferedReader(new FileReader(path + "\\instructor-detail.csv"));
		while((line = reader.readLine()) != null){
			String [] details = line.split(",");
			InstructorDetail instructorDetail = new InstructorDetail(details[0], details[1]);
			instructordao.save(instructorDetail);
			count++;
		}
		reader.close();
		return count;
	}

	private int writeinstructors(String path, instructordao instructordao, int key) throws Exception{
		int writecount=0;
		String line;
		List<InstructorDetail> instructordetails = instructordao.getInstructorDetailList(key);
		BufferedWriter writer = new BufferedWriter(new FileWriter(path + "\\instructordetails.csv"));
		for (InstructorDetail iDetail : instructordetails) {
			line = null;
			line = iDetail.getInstructorId() + "," + iDetail.getYouTubeChannel() + "," + iDetail.getHobby();
			writer.write(line);
			writer.newLine();
			writecount++;
		}
		writer.close();
		return writecount;
	}

	private void updateInstructor(int key, instructordao instructordao) {
		InstructorDetail instructor = instructordao.getInstructorDetailbyId(key);
		instructor.setHobby("Science and Technology");
		instructordao.update(instructor);
	}

	private void deleteInstructorbyId(int key, instructordao instructordao){
		InstructorDetail instructor = instructordao.getInstructorDetailbyId(key);
		if(instructor == null){
			System.out.println("Instructor doesn't exist");
		}else{
			instructordao.deleteInstructor(instructor.getInstructorId());
		}
	}

	private int deletespecifiedHobby(String hobby, instructordao instructordao){
		int numberofrecordsdeleted = instructordao.deletebyHobby(hobby);
		return numberofrecordsdeleted;
	}
}
