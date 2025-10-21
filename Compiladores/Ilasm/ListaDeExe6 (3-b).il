//begin
//   int ano, idade;
//   read ("Digite o ano de nascimento: ", ano);
//   idade = 2025 - ano;
//   if idade < 16
//      print ("representada");
//   else if idade < 18
//      print ("assistida");
//      else
//         print ("maior de idade");
//      end;
//   end;
//end 


.assembly extern mscorlib {}
.assembly teste_f{}
.module teste_f.exe

.class public teste_f{
  .method static public void _principal(){
   .entrypoint

   .locals (int64 ano, int64 idade)
   ldstr "Digite o ano de nascimento: "
   call void
        [mscorlib]System.Console::Write(string)
   call string
        [mscorlib]System.Console::ReadLine()
   call int64 
        [mscorlib]System.Int64::Parse(string)
   stloc ano
   ldc.r8 2025
   ldloc ano 
   conv.r8
   sub
   conv.i8
   stloc idade
   ldloc idade
   conv.r8
   ldc.r8 16
   clt
   brfalse L1
   ldstr "representada"
   call void
        [mscorlib]System.Console::Write(string)
br L2
L1:
   ldloc idade
   conv.r8
   ldc.r8 19
   clt
   brfalse L3
   ldstr "assistida"
   call void
        [mscorlib]System.Console::Write(string)
br L4
L3:
   ldstr "maior de idade"
   call void
        [mscorlib]System.Console::Write(string)
L4:
L2:


   ret
  }
}

      
      