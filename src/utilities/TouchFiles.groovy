package utilities

import filemanagement.LineFile

import static groovy.io.FileType.FILES

class TouchFiles {

    AddRemove addRemove = AddRemove.REMOVE

    enum AddRemove {
        ADD(1),
        REMOVE(2)
        AddRemove(int value) {}
    }

    def root = "C:\\Users\\s0041664\\Documents\\Development\\Groovy Development\\test"

    static main(args) {
        new TouchFiles()
    }

    TouchFiles() {
        start()
    }

    def start() {
        def rootDir = new File(root)
        rootDir.traverse(type: FILES, nameFilter: ~/.*\.groovy$/) {
            touchEachFile(it)
        }
    }

    def touchEachFile(File nextFile) {
        LineFile touchedFile = new LineFile(nextFile.path)
        LineFile outputFile = createOutputFileFromTouchedFile(touchedFile)
        if (addRemove == AddRemove.ADD) {
            copyWithoutTouchedLineToOutputFile(touchedFile, outputFile)
            addTouchedLineToOutputFile(outputFile)

        } else {
            copyWithoutTouchedLineToOutputFile(touchedFile, outputFile)
        }
        renameNewAndOld(touchedFile, outputFile)
    }

    def createOutputFileFromTouchedFile(LineFile touchedFile) {
        def filePath = touchedFile.file.parent
        def fileName = touchedFile.file.name
        def outputFileName = filePath + "\\" + fileName + "_new"
        new LineFile(outputFileName, filemanagement.BaseFile.CreateFlag.CREATE)
    }

    def copyWithoutTouchedLineToOutputFile(LineFile touchedFile, LineFile outputFile) {
        println "copying ${touchedFile.file.path} to ${outputFile.file.path} ${addRemove == AddRemove.ADD ? 'with' : 'without'} '//touched'"
        while (touchedFile.hasNext()) {
            String nextLine = touchedFile.next()
            if (nextLine.trim() != "//touched") {
                outputFile.writeLine(nextLine)
            }
        }
    }

    def addTouchedLineToOutputFile(LineFile outputFile) {
        outputFile.writeLine("//touched")
    }

    def renameNewAndOld(LineFile oldFile, LineFile newFile) {
        String oldFileName = oldFile.file.path
        oldFile.file.renameTo(oldFileName+"_old")
        String newFileName = newFile.file.path
        newFileName = newFileName.replaceAll(/_new/, "")
        newFile.file.renameTo(newFileName)
    }

}
