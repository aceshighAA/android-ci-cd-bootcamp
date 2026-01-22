#!/usr/bin/env bash
set -e

SARIF="app/build/reports/detekt/detekt.sarif"

if [ ! -f "$SARIF" ]; then
  echo "No SARIF file found"
  exit 0
fi

echo "## 🧹 Detekt Summary"

jq -r '
  .runs[].results[].ruleId
' "$SARIF" \
| sort \
| uniq -c \
| while read count rule; do
    echo "- **$rule**: $count"
  done
