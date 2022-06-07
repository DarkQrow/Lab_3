import java.util.Scanner;

public class Main {
    /*
    Краткое введение по структуре лабы для тех кто в танке, а точнее для меня же
    Данный метод применяется в UNIX системах(привет деление жесткого диска на блоки)
    Есть область данных INode - это так скажем, структура, по которой все процеммы по
    команде пользователя и будут делаться.
    Удивительно  но в методе "15 полей" всего 15 полей, но с приколами
    Первые 12 - просто список блоков с данными
    13 - блок данных + косвенная ссылка на первые 12(косвенная адрессация)
    14 - блок данных + косвенная ссылка на первые 12 + на 13ый(двойная косвенная адресация)
    15 - блок данных + косвенная ссылка на первые 12 + на 13ый + на 14ый(тройная косвенная адресация)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FS fs = new FS();

        while(true){
            System.out.println("Меню: \n" +
                    "1.Cоздание нового файла; \n" +
                    "2.Открытие существующего файла; \n" +
                    "3.Изменение текущей позиции в файле; \n" +
                    "4.Чтение блока данных из файла, начиная с текущей позиции \n" +
                    "5.Запись блока данных в файл с текущей позиции \n" +
                    "6.Закрытие файла; \n" +
                    "7.Удаление существующего файла; \n" +
                    "8.Поиск файлов и каталогов в заданном каталоге \n" +
                    "9.Создание каталога; \n" +
                    "10.Удаление пустого каталога; \n" +
                    "11.Изменение текущего каталога; \n" +
                    "12.Получение информации о текущем каталоге; \n" +
                    "13.Импорт всех данных из iNode в существующий файл \n" +
                    "14.Завершение работы \n");

            int choice = sc.nextInt();
            switch (choice){
                case 1 : {
                    System.out.println("Вы выбрали создание файла");
                    System.out.println("Введите имя для нового файла: ");
                    String line = sc.next();
                    fs.createNewFile(line);
                    break;
                }
                case 2: {
                    System.out.println("Производится открытие файла...");
                    fs.openFile();
                    break;
                }
                case 3: {
                    System.out.println("Производится изменение позиции в файле...");
                    System.out.println("Введите номер блока: ");
                    int num = sc.nextInt();
                    int part = 0;
                    if (num > 12) {
                        System.out.println("Введите номер части: ");
                        part = sc.nextInt();
                    }
                    fs.setPosition(num, part);
                    break;
                }
                case 4: {
                    System.out.println("Чтение блоков данных с текущей позиции:");
                    fs.readBlock();
                    break;
                }
                case 5: {
                    System.out.println("Запись блока данных в текущую позицию:");
                    System.out.println("Введите свои данные");
                    String text = sc.next();
                    fs.writeInBlock(text);
                    break;
                }
                case 6: {
                    System.out.println("Производится закрытие файла...");
                    fs.closeFile();
                    break;
                }
                case 7: {
                    System.out.println("Произовдится удаление файла...");
                    fs.deleteFile();
                    break;
                }
                case 8 : {
                    System.out.println("Производится поиск в каталоге...");
                    fs.searchInCat();
                    break;
                }
                case 9 : {
                    System.out.println("Вы брана опция создания каталога");
                    System.out.println("Введите новое имя каталога: ");
                    String name = sc.next();
                    fs.createCat(name);
                    break;
                }
                case 10: {
                    System.out.println("Производится удаление пустых каталогов");
                    fs.deleteEmptyCat();
                    break;
                }
                case 11: {
                    System.out.println("Переход в каталог для внесения изменений");
                    System.out.println("Введите путь к новому каталогу: ");
                    String absPath = sc.next();
                    fs.changeInCat(absPath);
                    break;
                }
                case 12: {
                    System.out.println("Выводится информация о каталоге");
                    fs.getInfo();
                    break;
                }
                case 13: {
                    System.out.println("Производится запись в реальный файл из временного...");
                    fs.rerunFromUs();
                    break;
                }
                case 14: {
                    System.out.println("Инициализация завершения работы...");
                    return;}
            }
        }

    }
}
