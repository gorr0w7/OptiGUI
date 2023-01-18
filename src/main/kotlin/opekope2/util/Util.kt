package opekope2.util

import net.minecraft.util.Identifier
import opekope2.optigui.resource.IResourceManager

/**
 * Runs a code block and returns its result.
 * If it raises and exception, suppresses it exceptions, and returns `null`
 */
internal inline fun <T> catchAll(function: () -> T): T? = try {
    function()
} catch (_: Exception) {
    null
}

/**
 * Converts the given string to a boolean:
 * - `true`, if the string is "true" (case-insensitive)
 * - `false`, if the string is "false" (case-insensitive)
 * - `null` otherwise
 */
internal fun String.toBoolean(): Boolean? = lowercase().toBooleanStrictOrNull()

/**
 * Resolves an OptiFine-compatible PNG image resource by appending the extension if necessary.
 *
 * @param id The resource identifier
 * @return The found identifier or `null` if not found
 */
internal fun IResourceManager.resolveResource(id: Identifier?): Identifier? {
    if (resourceExists(id ?: return null)) return id

    val idPng = Identifier(id.namespace, "${id.path}.png")
    return if (resourceExists(idPng)) idPng else null
}

/**
 * Trim parentheses from the start and end of a string
 */
fun String.trimParentheses() = trimStart('(').trimEnd(')')

/**
 * Splits a string at the given delimiters and returns every substring, which is not empty
 */
fun CharSequence.splitIgnoreEmpty(vararg delimiters: Char) =
    this.split(*delimiters).filter { it.isNotEmpty() }
