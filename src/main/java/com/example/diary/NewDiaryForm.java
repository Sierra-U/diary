package com.example.diary;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class NewDiaryForm{
	//新規投稿された日記本文
	@NotNull
	@Size(min=3, max=150, message = "文字数は３～１５０文字以内です。")
	private String newdiary;
}