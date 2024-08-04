$(document).ready(function() {
    bindDomEvent();
});

function bindDomEvent() {

    // 파일 입력 필드의 이벤트 핸들러 설정
    $(".custom-file-input").on("change", function() {
        var fileName = $(this).val().split("\\").pop();  // 이미지 파일명
        var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1); // 확장자 추출
        fileExt = fileExt.toLowerCase(); // 소문자 변환

        // 허용된 이미지 확장자 확인
        if (fileExt != "jpg" && fileExt != "jpeg" && fileExt != "gif" && fileExt != "png" && fileExt != "bmp") {
            alert("이미지 파일만 등록이 가능합니다.");
            return;
        }

        // 파일 이름을 라벨에 표시
        $(this).siblings(".custom-file-label").html(fileName);
    });
}