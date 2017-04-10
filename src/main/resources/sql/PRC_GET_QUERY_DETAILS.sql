create or replace PROCEDURE PRC_GET_QUERY_DETAILS(P_CHAT_TEXT IN VARCHAR2,
                                          M_SELECT_CLAUSE OUT VARCHAR2,
                                          M_TABLE_NAME OUT VARCHAR2,
                                          M_WHERE_CLAUSE OUT VARCHAR2) AS 

  qry   VARCHAR2(1000);
  input_string VARCHAR2(500);
  
  str_form1 varchar2(100);
  str_form2 varchar2(100);
  str_form3 varchar2(100);
  
  keyword1 varchar2(30);
  dynaword1 varchar2(30);
  wherespecial varchar2(30);
  
  
  match_String_key varchar2(20);
  match_String_dyna varchar2(20);
  match_String_where varchar2(20);
  
  tablename varchar2(50);
  fieldname varchar2(150);
  wherepart varchar2(100);
  wherecond1  varchar2(150);
  
  keyword_cnt number(2) :=0;
  cnt number(2):=0; 
  i number(2) := 1;
  match_count number(2) :=0;
  
    CURSOR LOOKUP_ROLE IS
      SELECT LOOKUP_CODE,LOOKUP_VALUE
           FROM tbl_mst_lookup
           WHERE LOOKUP_TYPE = 'EQI_ROLE'
           AND ACTIVEYN = 'Y';
    CURSOR LOOKUP_SKILL IS
      SELECT LOOKUP_CODE,LOOKUP_VALUE
           FROM tbl_mst_lookup
           WHERE LOOKUP_TYPE = 'EQI_SKILLSET'
           AND ACTIVEYN = 'Y';  
    CURSOR LOOKUP_PROJ IS
      SELECT LOOKUP_CODE,LOOKUP_VALUE
           FROM tbl_mst_lookup
           WHERE LOOKUP_TYPE = 'EQI_PROJECT'
           AND ACTIVEYN = 'Y';            
  BEGIN
    
     /* Remove below words from the given input*/
    select regexp_replace(P_CHAT_TEXT, '(Please)|(provide)|(pls)|(plz)|(give)|(take) |(tell)|(ping)| (me) |(detail)|(detais)|(how)|(many)|(list)|(out)|(of)|(who)|(share)|(currently)|(send)|(are)|(in) |(having)|(have)|(for)', '',1,0,'i') 
    into input_string
    from dual;
    
    str_form1 := trim(input_string);
    /* End */
    
    /* Remove more than one space between the words from output1*/
    str_form1:= regexp_replace(str_form1,'[[:space:]]+', ' ');
      
    DBMS_OUTPUT.PUT_LINE(input_string);
    DBMS_OUTPUT.PUT_LINE(str_form1);
    /* End */
    
    /* Check where keyword available in the above output(str_form1) , if found get the select clause and table name 
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
            keyword1 := trim(regexp_substr(str_form1, '[^ ]+',i,i) || ' ' || regexp_substr(str_form1, '[^ ]+',i,i+1));
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
                       ORDER BY tablename) 
            LOOP   
              BEGIN
              qry := 'select count(*) '|| match_count ||' from ' || t.tablename ||' where upper('|| t.columnname ||') = upper('''|| keyword1
                       || ''')';
                        dbms_output.put_line('qry ...............' ||qry);
              EXECUTE IMMEDIATE    
                  'select count(*)'||' from ' || t.tablename ||' where upper('|| t.columnname ||') = upper(''' || keyword1
                       || ''')' into match_count;
                   
                  IF match_count > 0 THEN
                   EXECUTE IMMEDIATE    
            
                  'select FIELDNAME,TABLENAME,WHERETCOND'||' from ' || t.tablename ||' where upper('|| t.columnname ||') = upper(''' || keyword1
                       || ''')' into M_SELECT_CLAUSE, M_TABLE_NAME,wherepart;
                 /* dbms_output.put_line('select_clause ...............' || M_SELECT_CLAUSE ||'............'|| M_TABLE_NAME);*/
                  keyword_cnt:=keyword_cnt+1;
                  END IF;
              EXCEPTION
                WHEN others THEN
                  dbms_output.put_line( 'Error encountered trying to read ' ||t.columnname || ' from ' || '.' || t.tablename );
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
    /* End */
    /*Remove matched table value like name , role , skill set etc from the input string */
     str_form2 := trim(regexp_replace(str_form1, match_String_key, '',1,0,'i'));
     dbms_output.put_line( 'select _clause......... ' ||M_SELECT_CLAUSE);
     dbms_output.put_line( 'Table Name......... ' ||M_TABLE_NAME);
      dbms_output.put_line( 'str_form2......... ' ||str_form2);
     /*end */
     /* Check where clause data available in the above output*/
     i:=1;
     match_count :=0;
   LOOP
      dynaword1 := regexp_substr(str_form2, '([^[:blank:]]+)', 1,  i);
       dbms_output.put_line( 'String......... ' ||dynaword1);
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
                 WHERE table_name NOT IN ('TBL_USERS','TBL_MST_LOOKUP','TBL_KEY_REFER')
                 AND column_name NOT IN('EMP_EMAIL')
                 ORDER BY tablename) 
      LOOP   
        BEGIN
       qry := 'select count(*) '|| match_count ||' from ' || t.tablename ||' where upper('|| t.columnname ||') like upper(''%'|| dynaword1
                 || '%'')';
                  dbms_output.put_line('qry ...............' ||qry);
        EXECUTE IMMEDIATE    
            'select count(*)'||' from ' || t.tablename ||' where upper('|| t.columnname ||') like upper(''%'|| dynaword1
                 || '%'')' into match_count;
         
          IF match_count > 0 THEN
            match_String_dyna := dynaword1;
            if (M_WHERE_CLAUSE is null OR M_WHERE_CLAUSE='') then
                  M_WHERE_CLAUSE:= M_WHERE_CLAUSE || ' upper(' || t.columnname ||') like upper(''%'|| dynaword1 || '%'')';
                else
                  M_WHERE_CLAUSE:= M_WHERE_CLAUSE || ' AND ' || ' upper(' || t.columnname ||') like upper(''%'|| dynaword1 || '%'')';
                END IF;  
            exit;
         END IF; 
        EXCEPTION
          WHEN others THEN
            dbms_output.put_line( 'Error encountered trying to read ' ||t.columnname || ' from ' || '.' || t.tablename );
        END;
      END LOOP;
     i := i+1;
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
        <<For2>>
         FOR no1 IN 1 .. cnt loop 
           i:=1;
           match_count :=0;
          LOOP
             if no1=1 then
                  wherespecial := trim(regexp_substr(str_form3, '[^ ]+',no1,i));
               elsif no1 > 1 then
                  wherespecial := trim(regexp_substr(str_form3, '[^ ]+',i,i) || ' ' || regexp_substr(str_form3, '[^ ]+',i,i+1));
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
                          || ') like upper(''%'
                          || wherespecial
                           || '%'')' ).extract ('ROWSET/ROW/*') ) ) t
                           WHERE table_name IN ('TBL_KEY_REFER')
                           and upper(column_name) like upper('%dynaword%')
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
                  EXECUTE IMMEDIATE
                  'select WHERECOND'||' from ' || t.tablename ||' where upper('|| t.columnname ||') = upper(''' || wherespecial
                                   || ''')' into wherecond1;
                END IF; 
                EXCEPTION
                    WHEN others THEN
                      dbms_output.put_line( 'Error encountered trying to read ' ||t.columnname || ' from ' || '.' || t.tablename );
                  END;
              END LOOP;
              dbms_output.put_line( match_count);
              IF match_count > 0 THEN
                match_String_where := wherespecial;
                dbms_output.put_line( match_String_where);
                dbms_output.put_line( wherecond1);
                if (M_WHERE_CLAUSE is null OR M_WHERE_CLAUSE='') then
                  M_WHERE_CLAUSE:= M_WHERE_CLAUSE || wherepart ||wherecond1;
                else
                  M_WHERE_CLAUSE:= M_WHERE_CLAUSE || ' AND ' ||wherepart || wherecond1;
                END IF;  
                exit For2 when match_count > 0;
             ELSE
                i := i+1;
             END IF;	
         END LOOP;
       END LOOP;  
    END IF;  
   dbms_output.put_line( 'M_WHERE_CLAUSE....... ' ||M_WHERE_CLAUSE );
  /*End*/       
END PRC_GET_QUERY_DETAILS;