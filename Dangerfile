# 変更行以外は指摘しない
github.dismiss_out_of_range_messages

checkstyle_format.base_path = Dir.pwd

# ktlint
Dir.glob('**/build/reports/ktlint/**.xml') do |file|
  checkstyle_format.report file
end

# Android Lint
Dir.glob('**/build/reports/lint/lint-result.xml') do |file|
  android_lint.report_file = file
  android_lint.filtering = true
  android_lint.skip_gradle_task = true
  android_lint.lint(inline_mode: true)
end

# JUnit test
junit.parse_files Dir.glob("**/build/test-results/**/TEST-*.xml")
junit.report

# Make it more obvious that a PR is a work in progress and shouldn't be merged yet
warn("PR is classed as Work in Progress") if github.pr_title.include? "[WIP]"
