//begin
//  int contador;
//  contador = 1;
//
//  float numero, resultado;
//  read (numero);
//  do 
//    resultado = numero * contador;
//    print (resultado, "   ");
//    contador = contador + 1;
//  until contador == 10;
//end


.assembly extern mscorlib {}
.assembly teste_f{}
.module teste_f.exe

.class public teste_f{
  .method static public void _principal(){
   .entrypoint
   
   .locals(int64 contador)
   ldc.i8 1
   conv.r8
   conv.i8
   stloc contador
   .locals(float64 numero, float64 resultado)
   call string
        [mscorlib]System.Console::ReadLine()
   call float64 
        [mscorlib]System.Double::Parse(string)
   stloc numero
L1:
   ldloc numero
   ldloc contador
   conv.r8
   mul
   stloc resultado
   ldloc resultado
   call void
         [mscorlib]System.Console::Write(float64)
   ldstr " "
   call void 
         [mscorlib]System.Console::Write(string)
   ldloc contador
   conv.r8
   ldc.r8 1
   add
   conv.i8
   stloc contador
   ldloc contador
   conv.r8
   ldc.r8 10
   ceq
   brfalse L1

   ret
  }
}
