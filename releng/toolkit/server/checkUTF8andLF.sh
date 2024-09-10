#!/bin/bash

set +e

pwd

{

#cat contributedFiles.txt | while read line; do
#  printf "alllines $line\n"
#done

if [ -f notASCII.log ] ; then
  rm notASCII.log
fi
if [ -f notUTF8.log ] ; then
  rm notUTF8.log
fi
if [ -f notLF.log ] ; then
  rm notLF.log 
fi

cd source


notASCII=()
notUTF8=()
notLF=()


# This command will fetch the path of the changed files
# This is the equivalent of a local: git show --pretty="" --name-only > contributedFiles.txt
# Then filters out the binary files if any were contributed as the UTF8 check is bound to fail on those
gerritHash=$(git rev-parse HEAD)
git diff-tree --no-commit-id --name-only -r $gerritHash | xargs grep -lIs . line > ../contributedFiles.txt
# Papyrus source is in to the "source/" folder of the workspace
#sed -i -e 's/^/.\/source\//' contributedFiles.txt


# We exclude the xtend files as they are required to be in ISO-8859-1 for some reasons
# Then it will verify if there are only ASCII characters, hence it is UTF8 by default
cat ../contributedFiles.txt | while read line; do
  if [ -f $line && $line != *.xtend && $line != *.properties ] ; then
    retval=$(tr -d \\000-\\177 < $line | wc -c)
    if [ "$retval" -gt "0" ] ; then
      notASCII+=$(echo "$line")
      echo "$line" >> ../notASCII.log
    fi
  fi
done

# If there are non ASCII characters we will now check if it is at least UTF8 compliant
cat ../contributedFiles.txt | while read line; do
  if [ -f $line && $line != *.xtend && $line != *.properties ] ; then
    iconv -f utf-8 -t ucs-4 < $line >/dev/null 2>&1
    retval=$?
    if [ "$retval" -ne 0 ] ; then
      notUTF8+=$(echo "$line")
      echo "$line" >> ../notUTF8.log
    fi
  fi
done

# This checks the end of line for the contributed files alone before checking the entire git base
cat ../contributedFiles.txt | while read line; do
  if [ -f $line ] ; then
    retval=$(grep -lIs $'\r$' $line)>/dev/null
    if [ -n "$retval" ] ; then
      notLF+=$(echo "$line")
      echo "$line" >> ../notLF.log
    fi
  fi
done

} &> /dev/null

#printf "%s" "${notASCII[@]}"
#printf "%s" "${notUTF8[@]}"
#printf "%s" "${notLF[@]}"
#cat ../contributedFiles.txt

if [ -f ../notUTF8.log ] ; then
  if [ $(cat ../notUTF8.log | wc -l) -gt 0 ] ; then
    echo "The contributed files are not UTF8 compliant. Please look at notUTF8.log"	
    cat ../notUTF8.log
    exit 1
  fi
else
  echo "All contributed files are UTF8 compliant"
fi

if [ -f ../notLF.log ] ; then
  if [ $(cat ../notLF.log | wc -l) -gt 0 ] ; then
    echo "There are non LF line endings in the contributed files. Please have a look at notLF.log"
    cat ../notLF.log
    exit 1
  fi
else
  echo "All contributed files respect the Linux line endings"
fi

cd ..

set -e

javac -version