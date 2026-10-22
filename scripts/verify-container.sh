#!/usr/bin/env bash
set -euo pipefail
image="${1:-calculator:v1}"

# Validate the packaged application and dependency loading inside the container.
actual="$(docker run --rm "$image")"
expected=$'12 + 3 = 15.0\n12 - 3 = 9.0\n12 * 3 = 36.0\n12 / 3 = 4.0'
printf '%s\n' "$actual"
if [[ "$actual" != "$expected" ]]; then
  echo 'Default arithmetic demonstration did not match expected output.' >&2
  exit 1
fi

# Also exercise the original argument-based interface with different operands.
[[ "$(docker run --rm "$image" 2 + 2)" == '4.0' ]]
[[ "$(docker run --rm "$image" 8 - 5)" == '3.0' ]]
[[ "$(docker run --rm "$image" 2 '*' 3)" == '6.0' ]]
[[ "$(docker run --rm "$image" 10 / 5)" == '2.0' ]]
if docker run --rm "$image" 10 / 0; then
  echo 'Division by zero unexpectedly succeeded.' >&2
  exit 1
fi
echo 'PASS: all four operations, CLI compatibility, and division-by-zero rejection.'
