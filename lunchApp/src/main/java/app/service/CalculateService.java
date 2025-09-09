package app.service;

import app.commons.CustomException;
import app.commons.ErrorCode;
import app.domain.Calculate;
import app.domain.Room;
import app.repository.RoomRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class CalculateService {
    Calculate calculate;
    private final RoomRepository roomRepository = new  RoomRepository();
    private final int MIN_AMOUNT = 1;
    private final int MAX_AMOUNT = 100000000;
    public void settleBill(Scanner sc, Room newRoom) {
        while (true) {
            calculate = new Calculate();
            if(isMealFinished()){
                setTotalPrice(sc, newRoom); // 결제 총 금액을 입력
                if(hasBlackKnight(sc)){  // 흑기사 여부 확인 trur면 있다. false면 없다.
                    payAsBlackKnight(sc, newRoom);
                }
                payInfo(newRoom);
                break;
            }
        }
    }

    //식사종료체크	 식사 후 종료 (버튼 선택)
    private boolean isMealFinished() {
        System.out.println("맛있게 드셨나요? 이제");

        return true;
    }

    //총 금액 확인	총 입력
    private void setTotalPrice(Scanner sc, Room newRoom) {
        while (true) {
            try{
                System.out.print("총 액을 입력해주세요. => ");
                int totalPrice = Integer.parseInt(sc.nextLine());
                validateAmount(totalPrice, newRoom); // 입력받은 총액을 검증한다.
                System.out.println("총 액이 " + calculate.getTotalPrice() + "원으로 설정 되었습니다.");
                break;
            }catch(NumberFormatException e){
                System.out.println("결제 금액은 숫자로 입력해주세요.");
            }catch(CustomException e){
                System.out.println(e.getMessage());
            }
        }
    }

    // 검증한 총액이 문제가 없다면 calculate에 총액과 인원수를 set한다.
    private void validateAmount(int totalPrice, Room newRoom) {
        if (totalPrice <= MIN_AMOUNT || totalPrice >= MAX_AMOUNT) throw new CustomException(ErrorCode.PAYMENT_AMOUNT_EXCEEDED);
        newRoom.setTotalPrice(totalPrice);
        calculate.setTotalPrice(totalPrice);
        calculate.setPersonCount(newRoom.getParticipantCount());
    }

    // 총액을 인원수랑 나눈 값
    private BigDecimal showPricePerPerson(int person, int totalPrice) {
        BigDecimal total = BigDecimal.valueOf(totalPrice);
        BigDecimal count = BigDecimal.valueOf(person);
        // 1인당 금액 계산 (소수점 올림)
        BigDecimal pricePerPerson = total.divide(count, 0, RoundingMode.UP);

        // 100원 단위 올림 처리
        BigDecimal remainder = pricePerPerson.remainder(BigDecimal.valueOf(100));
        if (remainder.compareTo(BigDecimal.ZERO) > 0) { // 0으로 나누어 지지 않다면 100으로 올림
            pricePerPerson = pricePerPerson.subtract(remainder).add(BigDecimal.valueOf(100));
        }
        return pricePerPerson;
    }

    private void payInfo(Room newRoom){
        BigDecimal NPrice = showPricePerPerson(calculate.getPersonCount(), calculate.getTotalPrice());
        if (calculate.getKingNum() == null) {
            System.out.println("1인당 금액은 " + NPrice + "원입니다.");
        } else {
            System.out.println(calculate.getKingNum() + "번이"+ calculate.getKingPrice() +"원이고 나머지 분은 1인당 금액은 " + NPrice + "원입니다." + " 남은 인원 " + calculate.getPersonCount());
        }
        System.out.println("   🎉 이용해주셔서 감사합니다! 🎉   ");
        roomRepository.saveRoom(newRoom);
    }

    // 흑기사가 있는지 체크 있다면  true 없다면 false
    private boolean hasBlackKnight(Scanner sc) {
        while(true){
            try{
                System.out.println("흑시가를 희망하시는 분있나요?");
                System.out.println("1. 접니다. 2. 없습니다.");
                System.out.print("번호를 입력해주세요. ==> ");
                String selectNum =  sc.nextLine();
                if(selectNum.equals("1"))   return true;
                else if(selectNum.equals("2"))  return false;
                else throw new CustomException(ErrorCode.INVALID_SELECTION.getMessage("1 또는 2를 입력해주세요."));
            }catch (CustomException e){
                System.out.println(e.getMessage());
            }
            catch (NumberFormatException e){
                System.out.println("흑기사를 원하시면 숫자로 입력해주세요.");
            }
        }

    }

    // 흑시가에서 1번을 눌렀을때
    private void payAsBlackKnight(Scanner sc, Room newRoom) {
        while(true) {
            try {
                BigDecimal NPrice = showPricePerPerson(calculate.getPersonCount(), calculate.getTotalPrice());
                System.out.println("총 인원 "+newRoom.getParticipantCount() +"명 입니다. 당신은 몇번의 사람입니까?");
                System.out.print("저의 번호는 => ");
                String selectNum = sc.nextLine();

                if (newRoom.getParticipantCount() < Integer.parseInt(selectNum))
                    throw new CustomException(ErrorCode.PERSON_NUMBER_OUT_OF_RANGE); // 범위를 벗어나면

                Integer.parseInt(selectNum); // 숫자인지 체크

                checkAmount(sc, newRoom, NPrice, selectNum);
                break;
            }catch (CustomException e){
                System.out.println(e.getMessage());
            }
            catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }
    private void checkAmount(Scanner sc, Room newRoom, BigDecimal NPrice, String selectNum) {
        while (true) {
            try {
                System.out.println(selectNum + "번님 총 금액이 " + newRoom.getTotalPrice() + "입니다.");
                System.out.print("1인당 " + NPrice + "원 입니다. 얼마를 더 내실껀가요? ==> ");
                int extraAmount = Integer.parseInt(sc.nextLine());  // 이 값이 1인당 값에서 포함해야하지
                if(extraAmount > (newRoom.getTotalPrice() - NPrice.intValue())) throw new CustomException(ErrorCode.EXTRA_AMOUNT_EXCEEDED);
                setCalculate(extraAmount, NPrice, newRoom, selectNum);
                break;
            }catch (CustomException e){
                System.out.println(e.getMessage());
            }
        }

    }

    private void setCalculate(int extraAmount, BigDecimal NPrice, Room newRoom, String selectNum) {
        calculate.setKingPrice(extraAmount + NPrice.intValue());
        calculate.setTotalPrice(newRoom.getTotalPrice() - calculate.getKingPrice());
        calculate.setPersonCount(calculate.getPersonCount() -1);
        calculate.setKingNum(selectNum);
    }




//    public static void main(String[] args) {
//        cal cal1 = new cal();
//        Scanner sc = new Scanner(System.in);
//        Room newRoom = new Room("방이름", 5, "나", 0, null);
//        cal1.settleBill(sc, newRoom);
//
//    }

}
//class cal{
//
//}


//총 금액 확인	총 금액을 보여준다.
//개인당 금액 확인	개인당 금액을 보여준다.
//기록 내보내기	정산내역(먹은내용 등) 텍스트파일로 export
//인원 수 만큼의 금액을 송금한다.
//금액 변경	총 금액에서 더 내고 싶은 액수를 제하고 다시 정산한다.