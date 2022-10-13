package com.comaecod.jogtracker.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jogging_data")
@Getter
@Setter
@NoArgsConstructor
public class Jog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer jogId;

	@Column(name = "date_and_time", nullable = false)
	private Date datetime;

	private String location;

	private String locationImg;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "jog", cascade = CascadeType.ALL)
	private Set<Intake> intake = new HashSet<>();

}
