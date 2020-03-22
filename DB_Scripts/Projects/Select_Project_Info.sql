SELECT proj.projname, projdescr, projinitstate , prjlabel.labelname , projectstate.statename , statetrans.currstate,statetrans.nextstate
                FROM project proj inner join projectlabel prjlabel 
                on proj.projname = prjlabel.projname 
                inner join projectstate on proj.projname = projectstate.projname
                inner join statetransitions statetrans on proj.projname = statetrans.projname
                where proj.projname = ?