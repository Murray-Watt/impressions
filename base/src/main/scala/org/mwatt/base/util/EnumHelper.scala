package org.mwatt.base.util

import scala.language.experimental.macros

object EnumHelper {
  def allValues[T]: Seq[T] = macro values_impl[T]

  def values_impl[A: c.WeakTypeTag](c: scala.reflect.macros.whitebox.Context): c.Expr[Seq[A]] = {
    import c.universe._

    val symbol = weakTypeOf[A].typeSymbol

    if (!symbol.isClass) {
      c.abort(c.enclosingPosition, "Only sealed traits or classes can be enumerated.")
    } else if (!symbol.asClass.isSealed) {
      c.abort(c.enclosingPosition, "Only sealed traits or classes can be enumerated.")
    } else {
      def allLeaves(clazz: c.universe.ClassSymbol): List[c.universe.Symbol] = {
        val directSubclasses = clazz.knownDirectSubclasses.toList
        directSubclasses.flatMap { childClass =>
          if (childClass.isModuleClass) {
            List(childClass)
          } else if (childClass.asClass.isSealed) {
            allLeaves(childClass.asClass)
          } else {
            c.abort(c.enclosingPosition, "Descendants must be sealed classes or objects.")
          }
        }
      }

      val children = allLeaves(symbol.asClass)

      if (!children.forall(_.isModuleClass)) {
        c.abort(c.enclosingPosition, "All children must be objects.")
      } else {
        c.Expr[Seq[A]] {
          def sourceModuleRef(sym: Symbol) = Ident(
            sym.asInstanceOf[
              scala.reflect.internal.Symbols#Symbol
            ].sourceModule.asInstanceOf[Symbol]
          )

          //noinspection ConvertibleToMethodValue
          Apply(
            Select(
              reify(Seq).tree,
              TermName("apply")
            ),
            children.map(sourceModuleRef(_))
          )
        }
      }
    }
  }
}


