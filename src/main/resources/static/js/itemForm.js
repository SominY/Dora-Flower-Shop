$(document).ready(function() {
    console.log("Document is ready."); // 디버깅용
    bindDomEvent();
});

function bindDomEvent() {

    console.log("File input changed"); // 디버깅 로그 추가

    // 파일 입력 필드의 이벤트 핸들러 설정
    $("#inputGroupFile").on("change", function() {
        var fileName = $(this).val().split("\\").pop();  // 이미지 파일명
        var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1); // 확장자 추출
        fileExt = fileExt.toLowerCase(); // 소문자 변환

        // 허용된 이미지 확장자 확인
        if (fileExt != "jpg" && fileExt != "jpeg" && fileExt != "gif" && fileExt != "png" && fileExt != "bmp") {
            alert("이미지 파일만 등록이 가능합니다.");
            $(this).val(''); // 파일 입력 필드를 빈값으로 설정
            return;
        }

        // 파일 이름을 라벨에 표시
        $(this).siblings("label").html(fileName);
    });
}