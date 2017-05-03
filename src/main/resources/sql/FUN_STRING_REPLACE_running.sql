CREATE OR REPLACE FUNCTION FUN_STRING_REPLACE(
    P_CHAT_TEXT IN VARCHAR2)
  RETURN VARCHAR2
AS
  cnt          NUMBER(2):=0;
  word_replace VARCHAR2(20);
  act_word     VARCHAR2(20);
  input_string VARCHAR2(200);
  i            NUMBER(2) := 1;
  qry          VARCHAR2(200);
  match_count  NUMBER(2) :=0;
BEGIN
  SELECT REGEXP_COUNT(P_CHAT_TEXT, '[^ ]+', 1,'i') INTO cnt FROM DUAL;
  FOR no IN 1 .. cnt
  LOOP
    IF no       =1 THEN
      act_word := trim(regexp_substr(P_CHAT_TEXT, '[^ ]+',no,i));
    elsif no    > 1 THEN
      act_word := trim(regexp_substr(P_CHAT_TEXT, '[^ ]+',1,i) || ' ' || regexp_substr(P_CHAT_TEXT, '[^ ]+',1,i+1));
    END IF;
    qry := 'select replace_string '|| word_replace ||' from tbl_word_replace where upper(act_string) = upper('''|| act_word || ''')';
    dbms_output.put_line('qry ...............' ||qry);
    EXECUTE IMMEDIATE 'select count(*) from tbl_word_replace where upper(act_string) = upper(''' || act_word || ''')' INTO match_count;
    IF match_count > 0 THEN
      EXECUTE IMMEDIATE 'select replace_string from tbl_word_replace where upper(act_string) = upper(''' || act_word || ''')' INTO word_replace;
      dbms_output.put_line('replace_string ...............' ||word_replace);
      IF (input_string IS NULL OR input_string='') THEN
        SELECT REGEXP_REPLACE(P_CHAT_TEXT, act_word,word_replace,1,0,'i')
        INTO input_string
        FROM dual;
      ELSE
        SELECT REGEXP_REPLACE(input_string, act_word,word_replace,1,0,'i')
        INTO input_string
        FROM dual;
      END IF;
    END IF;
    i:=i+1;
  END LOOP;
  DBMS_OUTPUT.PUT_LINE('input_string..........'||input_string);
  IF input_string IS NULL THEN
    RETURN P_CHAT_TEXT;
  ELSE
    RETURN input_string;
  END IF;
  EXCEPTION
  WHEN OTHERS THEN
    dbms_output.put_line( SQLERRM );
END FUN_STRING_REPLACE;