package org.example;

/**
 класс Stack представляет собой калькулятор для числовых выражений в инфиксной форме
 */
public class Calculator {
    /**
     поле, содержащее вычисляемое выражение
     */
    private String formula;

    /**
     конструктор по умолчанию
     */
    Calculator(){
        formula="";
    }

    /**
     конструктор по значению
     @param str - вводимое выражение
     */
    Calculator(String str){
        formula=str;
    }

    /**
     метод проверки правильности расстановки скобок
     @return true - если все расставлено верно; false - иначе
     */
    private boolean isCorrectSkobochka(){
        if (formula==""){//если пустая все верно
            return true;
        } else if (formula.charAt(0)==')'||formula.charAt(0)=='}'||formula.charAt(0)==']') { //если начали с закрытой неверно
            return false;
        }
        else{
            Stack<Character> g=new Stack<Character>();
            for (int i=0;i<formula.length();i++){
                switch(formula.charAt(i)){
                    case '(':
                    case '{':
                    case '[':{
                        g.push(formula.charAt(i));
                        break;
                    }
                    case')':{
                        if(g==null){//встретили закрывающуюся без открывающей
                            return false;
                        }
                        else{
                            Character x=g.remove();
                            if(x!='('){
                                return false;
                            }
                        }
                        break;
                    }
                    case'}':{
                        if(g==null){//встретили закрывающуюся без открывающей
                            return false;
                        }
                        else{
                            Character x=g.remove();
                            if(x!='{'){
                                return false;
                            }
                        }
                        break;
                    }
                    case']':{
                        if(g==null){//встретили закрывающуюся без открывающей
                            return false;
                        }
                        else{
                            Character x=g.remove();
                            if(x!='['){
                                return false;
                            }
                        }
                        break;
                    }
                    default:{
                        break;
                    }
                }

            }
            return true;
        }
    }


    /**
     метод для приведения выражения в нужный вид для верности вычислений
     */
    private void deleteSpaseAndConvertCommaInDot(){
        formula=formula.replace(" ","");
        formula=formula.replace(",",".");
    }

    /**
     метод проверки строки на то, можно ли ее представить числом типа double
     @return true - если все можно представить в виде double; false - иначе
     */
    private boolean isCorrectNumber(String num){
        if(num==""){
            return false;
        }
        else{
            String newNumber=num;
            if(newNumber.charAt(0)=='+'||newNumber.charAt(0)=='-'){
                newNumber=newNumber.substring(1);
            }
            for(int i=0;i<newNumber.length();i++){
                if(newNumber.charAt(i)<'0'||newNumber.charAt(i)>'9'||
                        newNumber.charAt(0)=='.'||newNumber.charAt(newNumber.length()-1)=='.'){
                    return false;
                }
            }
            return true;
        }
    }

    /**
     метод вычисления числового выражения в инфиксной форме
     @return решение выражения
     @throws IllegalArgumentException() - выражение не находится в инфиксной форме, либо оно не числовое
     */
    public double evaluateInfix(){
        if (formula=="") {
            throw new IllegalArgumentException();
        }
        else if(!isCorrectSkobochka()){//недопустимая формула
            throw new IllegalArgumentException();
        }
        else{
            deleteSpaseAndConvertCommaInDot();
            String s="("+formula+")";
            Stack<String> g=new Stack<String>();
            String number="";//будет накапливать число
            boolean operation=false;//есть ли операция
            for (int i=0;i<s.length();i++){
                switch(s.charAt(i)){
                    case '(':
                    case '{':
                    case '[': {
                        operation=false;
                        g.push(String.valueOf(s.charAt(i)));
                        break;
                    }
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                    case '.':{//накапливаем многозначное число
                        number+=String.valueOf(s.charAt(i));
                        break;
                    }
                    case '+':
                    case '*':
                    case '/':{
                        if (number!=""&&!operation){//значит число есть и операции еще не встречали
                            if(s.charAt(i+1)>='0'&&s.charAt(i+1)<='9'||
                                    s.charAt(i+1)=='('||s.charAt(i+1)=='['||s.charAt(i+1)=='{'){
                                //если после операции идет число то возможно вычисление
                                g.push(number);
                                g.push(String.valueOf(s.charAt(i)));
                                number="";
                                operation=true;
                            }
                            else{
                                throw new IllegalArgumentException();
                            }
                        }
                        else{//возможно перед операцией было выражение в скобках, значит оно уже вычислено в стеке
                            String nn=g.remove();
                            if((nn!="("||nn!="["||nn!="{")&&isCorrectNumber(nn)){
                                if(s.charAt(i+1)>='0'&&s.charAt(i+1)<='9'||
                                        s.charAt(i+1)=='('||s.charAt(i+1)=='['||s.charAt(i+1)=='{'){
                                    g.push(nn);
                                    g.push(String.valueOf(s.charAt(i)));
                                    number="";
                                    operation=true;
                                }
                                else{
                                    throw new IllegalArgumentException();
                                }
                            }
                            else {
                                throw new IllegalArgumentException();
                            }
                        }
                        break;
                    }
                    case '-':{
                        if((s.charAt(i-1)=='('||s.charAt(i-1)=='{'||s.charAt(i-1)=='[')
                                &&s.charAt(i+1)>='0'&&s.charAt(i+1)<='9'&&number=="")
                        {//значит это унарный минус от числа
                            number+=String.valueOf(s.charAt(i));
                        }
                        else if(number!=""&&!operation){
                            g.push(number);
                            g.push(String.valueOf(s.charAt(i)));
                            number="";
                            operation=true;
                        }
                        else{
                            throw new IllegalArgumentException();
                        }
                        break;
                    }
                    case ')':{
                        if(!operation && number==""){//это просто пустые скобки ()
                            String obj=g.remove();
                            if(obj!="("){
                                throw new IllegalArgumentException();
                            }
                            break;
                        }
                        else{
                            if(!operation && number!=""){//то есть у нас в скобках одно число
                                String obj=g.remove();
                                if(obj!="("){
                                    throw new IllegalArgumentException();
                                }
                                if(isCorrectNumber(number)){
                                    g.push(number);
                                    number="";
                                }
                                else{
                                    throw new IllegalArgumentException();
                                }
                                break;
                            }
                            else{
                                if(operation&&number!=""){//значит у нас есть и второе число и операция
                                    char operat=g.remove().charAt(0);
                                    number=number.replace(",",".");
                                    double number1=Double.parseDouble(g.remove().replace(",","."));
                                    double number2=Double.parseDouble(number);
                                    number="";
                                    operation=false;
                                    g.remove();//убрали лишнюю скобку
                                    double res;
                                    switch(operat){
                                        case '+':{
                                            res=number1+number2;
                                            break;
                                        }
                                        case '-':{
                                            res=number1-number2;
                                            break;
                                        }
                                        case '*':{
                                            res=number1*number2;
                                            break;
                                        }
                                        case '/':{
                                            res=number1/number2;
                                            break;
                                        }
                                        default:{
                                            throw new IllegalArgumentException();
                                        }
                                    }
                                    if(g.size()!=0) {
                                        char sss = g.remove().charAt(0);
                                        if (sss == '+' || sss == '-' || sss == '*' || sss == '/') {
                                            operation = true;
                                            number = res + "";
                                            g.push(sss + "");
                                        } else {
                                            g.push(sss + "");
                                            g.push(res + "");//добавили вычисленное число в стек
                                        }
                                    }
                                    else{
                                        g.push(res + "");//добавили вычисленное число в стек
                                    }
                                }
                                else{//есть операция но нет числа
                                    throw new IllegalArgumentException();
                                }
                            }
                        }
                        break;
                    }
                    case '}':{
                        if(!operation && number==""){//это просто пустые скобки {}
                            String obj=g.remove();
                            if(obj!="{"){
                                throw new IllegalArgumentException();
                            }
                            break;
                        }
                        else{
                            if(!operation && number!=""){//то есть у нас в скобках одно число
                                String obj=g.remove();
                                if(obj!="{"){
                                    throw new IllegalArgumentException();
                                }
                                if(isCorrectNumber(number)){
                                    g.push(number);
                                    number="";
                                }
                                else{
                                    throw new IllegalArgumentException();
                                }
                                break;
                            }
                            else{
                                if(operation&&number!=""){//значит у нас есть и второе число и операция
                                    char operat=g.remove().charAt(0);
                                    double number1=Double.parseDouble(g.remove());
                                    double number2=Double.parseDouble(number);
                                    number="";
                                    operation=false;
                                    g.remove();//убрали лишнюю скобку
                                    double res;
                                    switch(operat){
                                        case '+':{
                                            res=number1+number2;
                                            break;
                                        }
                                        case '-':{
                                            res=number1-number2;
                                            break;
                                        }
                                        case '*':{
                                            res=number1*number2;
                                            break;
                                        }
                                        case '/':{
                                            res=number1/number2;
                                            break;
                                        }
                                        default:{
                                            throw new IllegalArgumentException();
                                        }
                                    }
                                    if(g.size()!=0) {
                                        char sss = g.remove().charAt(0);
                                        if (sss == '+' || sss == '-' || sss == '*' || sss == '/') {
                                            operation = true;
                                            number = res + "";
                                            g.push(sss + "");
                                        } else {
                                            g.push(sss + "");
                                            g.push(res + "");//добавили вычисленное число в стек
                                        }
                                    }
                                    else{
                                        g.push(res + "");//добавили вычисленное число в стек
                                    }
                                }
                                else{//есть операция но нет числа
                                    throw new IllegalArgumentException();
                                }
                            }
                        }
                        break;
                    }
                    case ']':{
                        if(!operation && number==""){//это просто пустые скобки []
                            String obj=g.remove();
                            if(obj!="["){
                                throw new IllegalArgumentException();
                            }
                            break;
                        }
                        else{
                            if(!operation && number!=""){//то есть у нас в скобках одно число
                                String obj=g.remove();
                                if(obj!="["){
                                    throw new IllegalArgumentException();
                                }
                                if(isCorrectNumber(number)){
                                    g.push(number);
                                    number="";
                                }
                                else{
                                    throw new IllegalArgumentException();
                                }
                                break;
                            }
                            else{
                                if(operation&&number!=""){//значит у нас есть и второе число и операция
                                    char operat=g.remove().charAt(0);
                                    double number1=Double.parseDouble(g.remove());
                                    double number2=Double.parseDouble(number);
                                    number="";
                                    operation=false;
                                    g.remove();//убрали лишнюю скобку
                                    double res;
                                    switch(operat){
                                        case '+':{
                                            res=number1+number2;
                                            break;
                                        }
                                        case '-':{
                                            res=number1-number2;
                                            break;
                                        }
                                        case '*':{
                                            res=number1*number2;
                                            break;
                                        }
                                        case '/':{
                                            res=number1/number2;
                                            break;
                                        }
                                        default:{
                                            throw new IllegalArgumentException();
                                        }
                                    }
                                    if(g.size()!=0) {
                                        char sss = g.remove().charAt(0);
                                        if (sss == '+' || sss == '-' || sss == '*' || sss == '/') {
                                            operation = true;
                                            number = res + "";
                                            g.push(sss + "");
                                        } else {
                                            g.push(sss + "");
                                            g.push(res + "");//добавили вычисленное число в стек
                                        }
                                    }
                                    else{
                                        g.push(res + "");//добавили вычисленное число в стек
                                    }
                                }
                                else{//есть операция но нет числа
                                    throw new IllegalArgumentException();
                                }
                            }
                        }
                        break;
                    }
                    default:{//значит в формуле что-то лишнее
                        throw new IllegalArgumentException();
                    }
                }
            }
            //на данный момент если все хорошо в стеке находится ответ
            if(g.size()==1) {
                String sss=g.remove();
                while(sss.charAt(sss.length()-1)=='0'){
                    sss=sss.substring(0,sss.length()-1);
                }
               return Double.parseDouble(sss);
            }
            else{//значит в стеке что-то лишнее. Что-то пошло не так
                throw new IllegalArgumentException();
            }
        }

    }

}
