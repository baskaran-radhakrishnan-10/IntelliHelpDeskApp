create or replace PROCEDURE PRC_GET_QUERY_DETAILS(P_CHAT_TEXT IN VARCHAR2,
                                          M_SELECT_CLAUSE OUT VARCHAR2,
                                          M_TABLE_NAME OUT VARCHAR2,
                                          M_WHERE_CLAUSE OUT VARCHAR2) AS 

  qry   VARCHAR2(500);
  mst_qry   VARCHAR2(500);
  input_string VARCHAR2(500);
 
  str_form1 varchar2(100);
  str_form2 varchar2(100);
  str_form3 varchar2(100);
  
  keyword1 varchar2(30);
  dynaword1 varchar2(30);
  wherespecial varchar2(30):='';
  dynaword2 varchar2(30);
  wherekeytable varchar2(30):='';
  
  
  match_String_key varchar2(20);
  match_String_dyna varchar2(20);
  match_String_where varchar2(20);
  
  tablename varchar2(50);
  fieldname varchar2(150);
  colname varchar2(100);
  lookuptype varchar2(30);
  wherepart varchar2(100):='';
  wherecond1  varchar2(150):='';
  keytablename varchar2(50);
  keycolumnname varchar2(50);
  keycolumnname1 varchar2(50);
  datecolumn  Date;
  monthyear varchar2(10);
  datecolumnval varchar2(100);
  dayval number(3):=0;
  
  keyword_cnt number(2) :=0;
  cnt number(2):=0; 
  i number(2) := 1;
  match_count number(2) :=0;
  
  BEGIN
    
      /* Remove words configured in the table tbl_word_replace from the given input*/
         input_string:= FUN_STRING_REPLACE(P_CHAT_TEXT);
         dbms_output.put_line('After Function Call............' ||input_string);
         /* Remove below words after out put from function calling*/
         select regexp_replace(input_string, '(team)|(new)|(about)|(all)|(Please)|(provide)|(pls)|(plz)|(give)|(take) |(tell)|(ping)| (me) |(detail)|(detais)|(how)|(many)|(list)|(out)|(of) |(who)|(share)|(currently)|(send)|(are)| (in)|(having)|(have)|(for)|(to) ', '',1,0,'i') 
         into input_string
         from dual;
                  
         dbms_output.put_line('input_string ...............' ||input_string);
         str_form1 := trim(input_string);
        /* End */
    
        /* Remove more than one space between the words from output1*/
          str_form1:= regexp_replace(str_form1,'[[:space:]]+', ' ');
          DBMS_OUTPUT.PUT_LINE(str_form1);
        /* End */
    
    /* Check configured keyword available in the above output (str_form1) , if found get the select clause and table name 
       and remove that particular key word from the string  str_form1*/
        begin   
          select REGEXP_COUNT(str_form1, '[^ ]+', 1,'i') into cnt from dual;
          /*DBMS_OUTPUT.PUT_LINE('total Iteration : '||cnt);*/
          <<For1>>
          FOR no IN 1 .. cnt loop   
          i:=1;
          match_count:=0;
          LOOP
              if no=1 then
                keyword1 := trim(regexp_substr(str_form1, '[^ ]+',no,i));
              elsif no > 1 then
                keyword1 := trim(regexp_substr(str_form1, '[^ ]+',1,i) || ' ' || regexp_substr(str_form1, '[^ ]+',1,i+1));
             end if;
             EXIT WHEN keyword1 is null;
              dbms_output.put_line('keyword1 ...............' ||keyword1);
              FOR t IN (SELECT DISTINCT SUBSTR (keyword1, 1, 11) Searchword,
                          SUBSTR (table_name, 1, 25) tablename,
                          SUBSTR (column_name, 1, 20) columnname
                          FROM cols,
                          TABLE (xmlsequence (dbms_xmlgen.getxmltype ('select '
                          || column_name
                           || ' from '
                           || table_name
                          || ' where upper('
                           || column_name
                          || ') = upper('''
                          || keyword1
                           || ''')' ).extract ('ROWSET/ROW/*') ) ) t
                           WHERE table_name IN ('TBL_KEY_REFER')
                           and upper(column_name) like upper('%keyword%')
                           and column_name not in ('GKEY')
                           ORDER BY tablename) 
                LOOP   
                  BEGIN
                  qry := 'select count(*) '|| match_count ||' from ' || t.tablename ||' where upper('|| t.columnname ||') = upper('''|| keyword1
                           || ''')';
                            dbms_output.put_line('qry ...............' ||qry);
                            keytablename:=t.tablename;
                            keycolumnname := t.columnname;
                  EXECUTE IMMEDIATE    
                      'select count(*)'||' from ' || t.tablename ||' where upper('|| t.columnname ||') = upper(''' || keyword1
                           || ''')' into match_count;
                       dbms_output.put_line('keycolumnname ...............' ||t.columnname);
                      IF match_count > 0 THEN
                      qry := 'select FIELDNAME,TABLENAME'||' from ' || t.tablename ||' where upper('|| t.columnname ||') = upper(''' || keyword1
                           || ''')' ;
                             dbms_output.put_line( 'qry........' ||qry );
                       EXECUTE IMMEDIATE    
                      'select FIELDNAME,TABLENAME'||' from ' || t.tablename ||' where upper('|| t.columnname ||') = upper(''' || keyword1
                           || ''')' into M_SELECT_CLAUSE, M_TABLE_NAME;
                     /* dbms_output.put_line('select_clause ...............' || M_SELECT_CLAUSE ||'............'|| M_TABLE_NAME);*/
                      keyword_cnt:=keyword_cnt+1;
                      END IF;
                  EXCEPTION
                    WHEN others THEN
                      dbms_output.put_line( SQLERRM );
                  END;
                END LOOP;
                 IF match_count > 0 THEN
                  match_String_key :=keyword1;
                  exit For1 when match_count > 0;
                 ELSE
                  i := i+1;
                END IF;
               
            END LOOP;
          END LOOP;
          exception
          when others then
           DBMS_OUTPUT.PUT_LINE (SQLERRM);
          end;
 
    /* End  */
    /*Remove matched table value like name , role , skill set etc from the input string */
     str_form2 := trim(regexp_replace(str_form1, match_String_key, '',1,0,'i'));
     dbms_output.put_line( 'select _clause......... ' ||M_SELECT_CLAUSE);
     dbms_output.put_line( 'Table Name......... ' ||M_TABLE_NAME);
     dbms_output.put_line( 'str_form2......... ' ||str_form2);
     /*end */
     /* Check where clause data available in the above output*/
     cnt :=0;
     select REGEXP_COUNT(str_form2, '[^ ]+', 1,'i') into cnt from dual;
     <<For2>>
     FOR no2 IN 1 .. cnt loop 
     i:=1;
     match_count :=0;
    LOOP
        if no2=1 then
          dynaword1 := trim(regexp_substr(str_form2, '[^ ]+',no2,i));
        elsif no2 > 1 then
          dynaword1 := trim(regexp_substr(str_form2, '[^ ]+',1,i) || ' ' || regexp_substr(str_form2, '[^ ]+',1,i+1));
        end if;		
        dbms_output.put_line( 'String......... ' ||dynaword1);
          match_count :=0;
        /*dynaword1 := dynaword1 || ',';*/
        EXIT WHEN dynaword1 IS NULL;
        
      FOR t IN (SELECT DISTINCT SUBSTR (dynaword1, 1, 11) Searchword,
                SUBSTR (table_name, 1, 25) tablename,
                SUBSTR (column_name, 1, 20) columnname
                FROM cols,
                TABLE (xmlsequence (dbms_xmlgen.getxmltype ('select '
                || column_name
                 || ' from '
                 || table_name
                || ' where upper('
                 || column_name
                || ') like upper(''%'
                || dynaword1
                 || '%'')' ).extract ('ROWSET/ROW/*') ) ) t
                 WHERE table_name IN ('TBL_MST_LOOKUP')
                 and column_name in ('LOOKUP_DESC')
                 ORDER BY tablename) 
      LOOP   
        BEGIN
      
       qry := 'select count(*) '|| match_count ||' from ' || t.tablename ||' where upper('|| t.columnname ||') like upper(''%'|| dynaword1
                 || '%'')';
                 
                 dbms_output.put_line('dynaword1.......'|| qry );
          
        EXECUTE IMMEDIATE    
            'select count(*)'||' from ' || t.tablename ||' where upper('|| t.columnname ||') like upper(''%'|| dynaword1
                 || '%'') ' into match_count;
         
          IF match_count > 0 THEN
              
               mst_qry := 'select column_name,lookup_type '||' from ' || t.tablename ||' where upper('|| t.columnname ||') like upper(''%' || dynaword1
                                   || '%'') and table_name = ''' || M_TABLE_NAME || '''' ;
                 dbms_output.put_line('dynaword2.......'|| mst_qry );
                 
            EXECUTE IMMEDIATE mst_qry into colname,lookuptype;
            
               if (M_WHERE_CLAUSE is null OR M_WHERE_CLAUSE='') then
                  M_WHERE_CLAUSE:= M_WHERE_CLAUSE || ' upper(' || colname ||') like upper(''%'|| dynaword1 || '%'')';
                else
                  M_WHERE_CLAUSE:= M_WHERE_CLAUSE || ' AND ' || ' upper(' || colname ||') like upper(''%'|| dynaword1 || '%'')';
                END IF;  
         END IF; 
        EXCEPTION
            when NO_DATA_FOUND  then
                qry := 'select lookup_type '|| lookuptype ||' from ' || t.tablename ||' where upper('|| t.columnname ||') like upper(''%'|| dynaword1
                 || '%'')';
                 dbms_output.put_line('dynaword2.......'|| qry );
                  EXECUTE IMMEDIATE
              'select lookup_type '||' from ' || t.tablename ||' where upper('|| t.columnname ||') like upper(''%' || dynaword1
                                     || '%'')' into lookuptype ;
                dbms_output.put_line('lookuptype.......'|| lookuptype );
                  IF lookuptype = 'MONTH' THEN
                    dynaword2:= upper(dynaword1);
                    if (length(dynaword2) =3) then
                      SELECT DECODE(dynaword2,'JAN','01','FEB','02','MAR','03','APR','04','MAY','05','JUN','06','JUL','07','AUG','08','SEP','09','OCT','10','NOV','11','DEC','12')
                      INTO dynaword2
                      FROM DUAL;
                    else
                      SELECT DECODE(dynaword2,'JANUARY','01','FEBRUARY','02','MARCH','03','APRIL','04','MAY','05','JUNE','06','JULY','07','AUGEST','08','SEPTEMBER','09','OCTOBER','10','NOVEMBER','11','DECEMBER','12')
                      INTO dynaword2
                      FROM DUAL;
                    end if;  
                    dbms_output.put_line('after decode.......'|| dynaword2 );
                   
                    if monthyear is null then
                      monthyear := dynaword2;
                    else
                      monthyear:='';
                      monthyear:= dynaword2;
                    end if;  
                    dbms_output.put_line('dynaword2.......'|| dynaword2 );
                    dbms_output.put_line('monthyear.......'|| monthyear );
                  ELSIF lookuptype = 'YEAR'  THEN
                     if dynaword2 is null then
                       monthyear:=dynaword1;
                     else
                       monthyear:= monthyear||'/'||dynaword1;
                     end if;  
                     dbms_output.put_line( 'dynaword2.....' ||dynaword2);
                     dbms_output.put_line('monthyear.......'|| monthyear );
                  END IF;
                if (datecolumnval is null or datecolumnval='') and (length(monthyear) > 3)  then
                  datecolumnval:= chr(39)||trim(to_char(extract (day from TRUNC(TO_DATE(monthyear,'MM/YYYY'))),'09'))||'/'||monthyear||chr(39);
                elsif(datecolumnval is not null and (length(monthyear) > 3)) then
                   datecolumnval:= datecolumnval || ' and '||chr(39)||extract(day from trunc(last_day(to_date(monthyear,'MM/YYYY'))))||'/'||monthyear||chr(39);
                end if;
                dbms_output.put_line( 'datecolumnval.....' ||datecolumnval);
              WHEN others THEN
            dbms_output.put_line( 'Error encountered trying to read ' ||SQLERRM );
           END;
      END LOOP;
          IF match_count > 0 THEN
              match_String_dyna := dynaword1;
              dbms_output.put_line( 'After Match LLLLLLLL......... ' ||dynaword1);
              if((regexp_instr(dynaword1,'(1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25)')=1) and length(dynaword1)<=3 ) then
                dbms_output.put_line( 'if,,,,,,,'||dynaword1);
                dayval:=to_number(dynaword1,'99');
              end if;
              str_form2 := trim(regexp_replace(str_form2, match_String_dyna, '',1,0,'i'));
              dbms_output.put_line( 'dayval......... ' ||dayval);
              dbms_output.put_line( 'After Match......... ' ||str_form2);
          /* exit For2 when match_count > 0;*/
             ELSE
              i := i+1;
            END IF;      
        END LOOP;
        
        END LOOP;
       /*End*/
    /*Remove matched table value like name , role , skill set etc from the input string */
		 str_form3 := trim(regexp_replace(str_form2, match_String_dyna, '',1,0,'i'));
     M_WHERE_CLAUSE := trim(M_WHERE_CLAUSE);
		 dbms_output.put_line( 'Where Clause......... ' ||M_WHERE_CLAUSE);
		 dbms_output.put_line( 'str_form3......... ' ||str_form3);
     /*end */
     if str_form3 is not null then
        /* Check where clause data available in the above output*/
        cnt :=0;
        select REGEXP_COUNT(str_form3, '[^ ]+', 1,'i') into cnt from dual;
        <<For3>>
         FOR no1 IN 1 .. cnt loop 
           i:=1;
           match_count :=0;
          LOOP
             if no1=1 then
                  wherespecial := trim(regexp_substr(str_form3, '[^ ]+',no1,i));
               elsif no1 > 1 then
                  wherespecial := trim(regexp_substr(str_form3, '[^ ]+',1,i) || ' ' || regexp_substr(str_form3, '[^ ]+',1,i+1));
             end if;		
             dbms_output.put_line( 'wherespecial......... ' ||wherespecial);
             EXIT WHEN wherespecial IS NULL;
             
             FOR t IN (SELECT DISTINCT SUBSTR (wherespecial, 1, 11) Searchword,
                          SUBSTR (table_name, 1, 25) tablename,
                          SUBSTR (column_name, 1, 20) columnname
                          FROM cols,
                          TABLE (xmlsequence (dbms_xmlgen.getxmltype ('select '
                          || column_name
                           || ' from '
                           || table_name
                          || ' where upper('
                           || column_name
                          || ') = upper('''
                          || wherespecial
                           || ''')' ).extract ('ROWSET/ROW/*') ) ) t
                           WHERE table_name IN ('TBL_KEY_REFER')
                           and upper(column_name) like upper('%dynaword%')
                           and column_name not in ('GKEY')
                           ORDER BY tablename) 
               LOOP   
                BEGIN
                 qry := 'select count(*) '|| match_count ||' from ' || t.tablename ||' where upper('|| t.columnname ||') = upper('''|| wherespecial
                           || ''')';
                            dbms_output.put_line('qry ...............' ||qry);
                  EXECUTE IMMEDIATE    
                      'select count(*)'||' from ' || t.tablename ||' where upper('|| t.columnname ||') = upper('''|| wherespecial
                           || ''')' into match_count;
                   
                IF match_count > 0 THEN
                            qry  := 'select WHERECOND,WHERETCOND from ' || t.tablename ||' where upper('|| t.columnname ||') = upper('''|| wherespecial
                           || ''') ';
                            dbms_output.put_line('qry ...............' ||qry);  
                  EXECUTE IMMEDIATE
                    'select WHERECOND,WHERETCOND'||' from ' || t.tablename ||' where upper('|| t.columnname ||') = upper(''' || wherespecial
                                    || ''')' into wherecond1,wherepart;
                                     
                END IF; 
                 
                EXCEPTION
                    WHEN NO_DATA_FOUND THEN
                     EXECUTE IMMEDIATE
                    'select WHERECOND,WHERETCOND'||' from ' || t.tablename ||' where upper('|| t.columnname ||') = upper(''' || wherespecial
                                     || ''') and upper(' || keycolumnname ||') = upper('''|| keyword1|| ''')' into wherecond1,wherepart;
                                     
                    WHEN others THEN
                      dbms_output.put_line( 'Error encountered trying to read ' ||t.columnname || ' from ' || '.' || t.tablename );
                       dbms_output.put_line( 'WHEN others THEN');
                        qry  := 'select WHERECOND,WHERETCOND from ' || t.tablename ||' where upper('|| t.columnname ||') = upper('''|| wherespecial
                           || ''') and upper(' || keycolumnname ||') = upper('''|| keyword1|| ''')';
                            dbms_output.put_line('qry ...............' ||qry);
                    EXECUTE IMMEDIATE
                    'select WHERECOND,WHERETCOND'||' from ' || t.tablename ||' where upper('|| t.columnname ||') = upper(''' || wherespecial
                                     || ''') and upper(' || keycolumnname ||') = upper('''|| keyword1|| ''')' into wherecond1,wherepart;
                  END;
              END LOOP;
              
              IF match_count > 0 THEN
                match_String_where := wherespecial;
                dbms_output.put_line( match_String_where);
                dbms_output.put_line('wherecond1 .....'||wherecond1);
                if (M_WHERE_CLAUSE is null OR M_WHERE_CLAUSE='') then
                  M_WHERE_CLAUSE:= M_WHERE_CLAUSE || wherepart ||wherecond1;
                else
                  M_WHERE_CLAUSE:= M_WHERE_CLAUSE || ' AND ' ||wherepart || wherecond1;
                END IF;  
                exit For3 when match_count > 0;
             ELSE
                i := i+1;
             END IF;	
         END LOOP;
       END LOOP;  
    END IF;  
   dbms_output.put_line( 'M_WHERE_CLAUSE....... ' ||M_WHERE_CLAUSE );
      dbms_output.put_line( 'match_String_key....... ' ||match_String_key );

  /*End*/
  /* Where clause column for date columns and number columns*/
       if(datecolumnval is not null) then
        wherecond1:='';
        mst_qry := 'select WHERECOND '||' from TBL_KEY_REFER where upper(keyword1) = upper(''' || match_String_key
                                   || ''') and dynaword1 is null and dynaword2 is null' ;
                                   
                 dbms_output.put_line('final query.......'|| mst_qry );
                 
            EXECUTE IMMEDIATE mst_qry into wherecond1;
        end if;    
     IF (M_WHERE_CLAUSE IS NULL) AND (datecolumnval IS NOT NULL) AND (INSTR(UPPER(datecolumnval),'AND')>0)  THEN
        M_WHERE_CLAUSE := wherecond1 || ' between ' || datecolumnval;
     ELSIF (M_WHERE_CLAUSE IS NULL) AND (datecolumnval IS NOT NULL) AND (INSTR(UPPER(str_form3),'TODAY')>0) THEN
        M_WHERE_CLAUSE := wherecond1 || ' between ' || datecolumnval || ' and ' ||chr(39)|| extract(DAY from trunc(SYSDATE))||'/'|| extract(MONTH from trunc(SYSDATE))||'/'|| extract(YEAR from trunc(SYSDATE))|| chr(39);
     END IF;
     IF (M_WHERE_CLAUSE IS NOT NULL AND dayval is not null and  wherecond1 is not null and dayval>0) then
        M_WHERE_CLAUSE:= M_WHERE_CLAUSE || ' = ' || dayval;
     elsif(M_WHERE_CLAUSE IS NULL AND dayval is not null and  wherecond1 is not null and dayval>0) then
        M_WHERE_CLAUSE:= M_WHERE_CLAUSE || ' = ' || dayval;
     end if;  
     dbms_output.put_line('Where clause column for date columns and number columns.....'||M_WHERE_CLAUSE);
 /* End */
 /* Where clause for tables which having key value in the table itself like HR policy,info burst etc.... */
     IF (M_WHERE_CLAUSE IS NULL AND str_form3 IS NOT NULL) THEN
       cnt :=0;
       select REGEXP_COUNT(str_form3, '[^ ]+', 1,'i') into cnt from dual;
       <<For4>>
         FOR no3 IN 1 .. cnt loop 
           i:=1;
           match_count :=0;
          LOOP
             if no3=1 then
                  wherekeytable := trim(regexp_substr(str_form3, '[^ ]+',no3,i));
               elsif no3 > 1 then
                  wherekeytable := trim(regexp_substr(str_form3, '[^ ]+',1,i) || ' ' || regexp_substr(str_form3, '[^ ]+',1,i+1));
             end if;		
             dbms_output.put_line( 'wherekeytable......... ' ||wherekeytable);
             EXIT WHEN wherekeytable IS NULL;
             
             FOR t IN (SELECT DISTINCT SUBSTR (wherekeytable, 1, 11) Searchword,
                          SUBSTR (table_name, 1, 25) tablename,
                          SUBSTR (column_name, 1, 20) columnname
                          FROM cols,
                          TABLE (xmlsequence (dbms_xmlgen.getxmltype ('select '
                          || column_name
                           || ' from '
                           || table_name
                          || ' where upper('
                           || column_name
                          || ') like upper(''%'
                          || wherekeytable
                           || '%'')' ).extract ('ROWSET/ROW/*') ) ) t
                           WHERE table_name IN (M_TABLE_NAME)
                           and column_name not in ('GKEY')
                           ORDER BY tablename) 
               LOOP   
                BEGIN
                 qry := 'select count(*) '|| match_count ||' from ' || t.tablename ||' where upper('|| t.columnname ||') like upper(''%'|| wherekeytable
                           ||  '%'')';
                            dbms_output.put_line('qry ...............' ||qry);
                  EXECUTE IMMEDIATE    
                      'select count(*)'||' from ' || t.tablename ||' where upper('|| t.columnname ||') like upper(''%'|| wherekeytable
                           || '%'')' into match_count;
                  IF match_count > 0 THEN
                   keycolumnname1 := t.columnname;
                  END IF;         
                    
                EXCEPTION
                    WHEN NO_DATA_FOUND THEN
                      dbms_output.put_line( SQLERRM );
                  END;
              END LOOP;
              
              IF match_count > 0 THEN
                match_String_where := wherekeytable;
                if (M_WHERE_CLAUSE is null OR M_WHERE_CLAUSE='') then
                  M_WHERE_CLAUSE:= M_WHERE_CLAUSE || ' upper(' || keycolumnname1 ||') like upper(''%'|| wherekeytable || '%'')';
                else
                  M_WHERE_CLAUSE:= M_WHERE_CLAUSE || ' AND ' || ' upper(' || keycolumnname1 ||') like upper(''%'|| wherekeytable || '%'')';
                 END IF;         
                exit For4 when match_count > 0;
             ELSE
                i := i+1;
             END IF;	
         END LOOP;
       END LOOP;  
           
     END IF;   
     dbms_output.put_line( M_WHERE_CLAUSE);
     M_WHERE_CLAUSE := REPLACE(M_WHERE_CLAUSE, '?',dayval);
     dbms_output.put_line( M_WHERE_CLAUSE);
  /* End */
END PRC_GET_QUERY_DETAILS;