package com.moontrader.controller.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.moontrader.dto.Comment;

@ManagedBean
@RequestScoped
public class CommentView implements Serializable{
	private static final long serialVersionUID = -4228819181143928200L;
	private List<Comment> comments=new ArrayList<Comment>();
	private Comment comment=new Comment();
	
}
