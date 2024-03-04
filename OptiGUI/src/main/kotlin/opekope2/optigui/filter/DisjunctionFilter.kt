package opekope2.optigui.filter

import opekope2.optigui.filter.IFilter.Result.*

/**
 * A filter, which applies the logical OR operation between the given filters and returns the result.
 * Only skips if all sub-filters skip, and only yields mismatch if no sub-filters yield match.
 *
 * @param T The type the filter accepts
 * @param filters The sub-filters to evaluate
 */
class DisjunctionFilter<T>(private val filters: Collection<IFilter<T, *>>) : IFilter<T, Unit>, Iterable<IFilter<T, *>> {
    /**
     * Alternative constructor with variable arguments
     *
     * @param filters The sub-filters to evaluate
     */
    constructor(vararg filters: IFilter<T, *>) : this(filters.toList())

    override fun evaluate(value: T): IFilter.Result<out Unit> = filters.map { it.evaluate(value) }.let { results ->
        var allSkip = true
        for (result in results) {
            when (result) {
                is Match -> return@let Match(Unit)
                is Mismatch -> allSkip = false
                else -> {}
            }
        }

        if (allSkip) Skip
        else Mismatch
    }

    override fun iterator(): Iterator<IFilter<T, *>> = filters.iterator()

    override fun toString(): String = javaClass.name
}
