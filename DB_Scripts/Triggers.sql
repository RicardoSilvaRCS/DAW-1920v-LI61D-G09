
CREATE trigger tr_deleteProject before delete
	on project 
	for each row
	execute procedure projectDelete();
	
	
create or replace FUNCTION projectDelete() RETURNS TRIGGER AS $_$
begin
	
	delete from issue where projname = old.projname;
    delete from projectlabel where projname = old.projname;
	delete from statetransitions where projname = old.projname;
	delete from projectstate where projname = old.projname;
   
    RETURN OLD;
end $_$ LANGUAGE 'plpgsql';	
	


CREATE trigger tr_deleteIssue before delete
	on issue 
	for each row
	execute procedure issueDelete()
	
CREATE or replace FUNCTION issueDelete() RETURNS TRIGGER AS $_$
begin
	
  	delete from issuelabel where issueid = old.id;
	delete from issuecomment where issueid = old.id;
   
    RETURN OLD;
END $_$ LANGUAGE 'plpgsql';	


delete from project where projname='SLB'












