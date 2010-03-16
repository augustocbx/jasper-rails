/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * JasperReports - Free Java report-generating library.
 * Copyright (C) 2001-2005 JasperSoft Corporation http://www.jaspersoft.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 *
 * JasperSoft Corporation
 * 303 Second Street, Suite 450 North
 * San Francisco, CA 94107
 * http://www.jaspersoft.com
 */
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;



/**
 * @author Marcel Overdijk (marceloverdijk@hotmail.com)
 * @version $Id: Person.java 1793 2007-07-30 09:06:18Z teodord $
 */
@Entity
public class Person {

	private int id;
	private String name;

	private java.util.Collection<Movie> movies;

	public Person() {
	}

	public Person(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy="director")
	public java.util.Collection<Movie> getMovies() {
		return this.movies;
	}

	public void setMovies(java.util.Collection<Movie> movies) {
		this.movies = movies;
	}
}
