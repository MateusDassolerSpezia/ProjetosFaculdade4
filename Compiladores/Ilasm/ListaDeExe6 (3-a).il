//begin
//   int base, altura;
//   float area;
//
//   read ("digite base: ", base, "digite altura: ", altura);
//   area = (base * altura) / 2;
//   print (area);
//end


.assembly extern mscorlib {}
.assembly teste_f{}
.module teste_f.exe

.class public teste_f{
  .method static public void _principal(){
   .entrypoint
   
   .locals (int64 base, int64 altura, float64 area)
   ldstr "digite base: "
   call void
        [mscorlib]System.Console::Write(string)
   call string
        [mscorlib]System.Console::ReadLine()
   call int64 
        [mscorlib]System.Int64::Parse(string)
   stloc base
   ldstr "digite altura: "
   call void
        [mscorlib]System.Console::Write(string)
   call string
        [mscorlib]System.Console::ReadLine()
   call int64 
        [mscorlib]System.Int64::Parse(string)
   stloc altura
   ldloc base
   conv.r8
   ldloc altura
   conv.r8
   mul
   ldc.r8 2
   div
   stloc area
   ldloc area
   call void
         [mscorlib]System.Console::Write(float64)

   ret
  }
}



