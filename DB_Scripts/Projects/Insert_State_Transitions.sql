--INSERT INTO public.statetransitions(
	--currstate, nextstate, projname)
	--VALUES (?, ?, ?);
	
--SLB Project	

INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('1', '2','SLB');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('2', '3','SLB');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('3','4','SLB');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('4','1','SLB');
	
---------------------------------//--------------------------------

--Sporting Project
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('50', '51','Sporting');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('51', '52','Sporting');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('52','53','Sporting');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('53','51','Sporting');
	
	
---------------------------------//--------------------------------

--Porto Project
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('40', '41','Porto');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('41', '42','Porto');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('42','43','Porto');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('43','41','Porto');
	

---------------------------------//--------------------------------

--Ari Project
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('OMagrinho', 'OComilao','Ari');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('OComilao', 'OGordo','Ari');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('OGordo','MagroOutraVez','Ari');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('MagroOutraVez','GordoOutravez','Ari');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('GordoOutravez','OMagrinho','Ari');
	
---------------------------------//--------------------------------

--Alex Project
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('OTropinha', 'FoiAInspecaoDasBolas','Alex');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('FoiAInspecaoDasBolas', 'Chumbou','Alex');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('Chumbou','SaiuDaTropa','Alex');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('SaiuDaTropa','FoiParaOIsel','Alex');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('FoiParaOIsel','OTropinha','Alex');
	
---------------------------------//--------------------------------

--Ricardo Project
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('OChunga', 'ChumbouEmFisicaEQuimica','Ricardo');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('ChumbouEmFisicaEQuimica', 'MudouDeOpinia','Ricardo');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('MudouDeOpinia','FoiParaInformatica','Ricardo');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('FoiParaInformatica','AgoraEstaNaMerda','Ricardo');
	
INSERT INTO public.statetransitions(
	currstate, nextstate, projname)
	VALUES ('AgoraEstaNaMerda','OChunga','Ricardo');
	
	
	
	