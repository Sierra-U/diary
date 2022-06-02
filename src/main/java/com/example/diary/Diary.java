package com.example.diary;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
			//@Table(name = "diary") は指定しないとクラス名のdiaryというテーブルが生成される
public class Diary{
	public Diary(String bodytext, LocalDateTime createDatetime) {
		this.bodytext = bodytext;
		this.createDatetime = createDatetime;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String bodytext;
	//投稿日時
	@Column(name = "create_datetime", nullable = false)
	private LocalDateTime createDatetime;
}