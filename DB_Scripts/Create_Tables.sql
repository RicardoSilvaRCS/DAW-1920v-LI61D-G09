 drop table IssueComment;
 drop table IssueLabel;
 drop table Issue;
 drop table StateTransitions;
 drop table ProjectState;
 drop table ProjectLabel;
 drop table Project;
 drop table Users;
 drop table FriendsList;
 drop table UsersProjects;

--||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||--

create table Project (
	projName varchar(64),
	projDescr text not null,
	projInitState varchar(64) not null,
	primary key (projName)
);

-- drop table Project;

--||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||--

create table ProjectLabel (
	labelName varchar(64) not null,
	projName varchar(64) not null,
	primary key (labelName, projName),
	
	constraint project_of_label foreign key (projName) 
		references Project(projName)
);

-- drop table ProjectLabel;

--||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||--

create table ProjectState (
	stateName varchar(64) not null,
	projName varchar(64) not null,
	primary key (stateName, projName),
		
	constraint project_of_state foreign key (projName)
		references Project(projName)
);

-- drop table ProjectState;

--||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||--

create table StateTransitions (
	currState varchar(64) not null,
	nextState varchar(64) not null, 
	projName varchar(64) not null,
	primary key (currState, nextState, projName),
	
	constraint project_of_currState foreign key (currState, projName)
		references ProjectState(stateName, projName),
			
	constraint project_of_nextState foreign key (nextState, projName)
		references ProjectState(stateName, projName)
);

-- drop table StateTransitions;

--||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||--

create table Issue (
	id serial,
	issueName varchar(64) not null,
	issueDescr varchar(128) not null,
	creationDate timestamp not null,
	updateDate timestamp not null,
	closeDate timestamp,
	projName varchar(64) not null,
	currState varchar(64) not null,
	primary key (id),
	unique(issueName, projName),
	
	constraint project_of_issue foreign key (projName)
		references Project(projName),
		
	constraint state_of_issue foreign key (currState, projName)
		references ProjectState(stateName, projName)
);

-- drop table Issue;

--||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||--

create table IssueLabel (
	issueId int not null,
	labelName varchar(64) not null,
	projName varchar(64) not null,
	primary key (issueId, labelName, projName),
	
	constraint issue_of_label foreign key (issueID)
		references Issue(id),
		
	constraint project_of_label foreign key (labelName, projName)
		references ProjectLabel(labelName, projName)
);

-- drop table IssueLabel;

--||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||--

create table IssueComment (
	commentId serial unique,
	commentText varchar(128) not null,
	commentCreation timestamp not null,
	issueId int not null,
	primary key (commentId),
	
	constraint issue_of_comment foreign key (issueId)
		references Issue(id)
);

-- drop table IssueComment;

--||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||--

create table Users (
	userName varchar(25) not null,
	fullName varchar(64) not null,
	email varchar(64) not null,
	age int ,  
	genre varchar(10),
	phoneNumber varchar(9),
	pass varchar(64) not null,
	
	Unique (userName,email),
	primary key (userName)
);

--drop table Users;

--||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||--

create table FriendsList (
	userName varchar(25),
	friendName varchar(25),
	
	constraint userName foreign key (userName) references Users (userName),
	constraint friendName foreign key (userName) references Users(userName),
	accepted int not null,
	
	primary key (userName, friendName)
);


--drop table FriendsList

--||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||--

create table UsersProjects (
	userName varchar(25),
	projName varchar(25),
	
	constraint userName foreign key (userName) references Users (userName),
	constraint projName foreign key (projName) references Project(projName),
	
	primary key (userName,projName)
); 

--drop table UsersProjects

--||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||--
