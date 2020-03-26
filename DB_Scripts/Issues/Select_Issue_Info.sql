SELECT issue.*, issuelabel.labelname
FROM issue 
    left join issuelabel on issue.id = issuelabel.issueid
where issue.id = 3 

