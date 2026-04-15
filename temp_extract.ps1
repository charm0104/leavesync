$word = New-Object -ComObject Word.Application
$doc = $word.Documents.Open("c:\Users\DELL 5418\OneDrive\Desktop\LEAVESYNC\leavesync_dashboard_changes.docx")
$text = $doc.Content.Text
Write-Output $text
$word.Quit()